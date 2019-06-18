package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

/**
 * 投稿者のリポジトリです.
 * 
 * @author kazuya.makida
 *
 */
@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};

	private static final ResultSetExtractor<List<Article>> ARTICLE_AND_COMMENT_ROW_MAPPER = (rs) -> {
		List<Article> articleList = new ArrayList<Article>();
		List<Comment> commentList = new ArrayList<Comment>();
		Article article = null;

		int beforeArticleId = 0;

		while (rs.next()) {
			if (beforeArticleId != rs.getInt("article_id")) {

				article = new Article();

				article.setId(rs.getInt("article_id"));
				article.setName(rs.getString("article_name"));
				article.setContent(rs.getString("article_content"));

				commentList = new ArrayList<Comment>();
				article.setCommentList(commentList);
				articleList.add(article);
			}
			
			if (rs.getString("comment_name") != null) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("comment_id"));
				comment.setName(rs.getString("comment_name"));
				comment.setContent(rs.getString("comment_content"));
				
				commentList.add(comment);				
			}

			beforeArticleId = rs.getInt("article_id");
		}

		return articleList;
	};

	/**
	 * 投稿情報をリストに追加します.
	 * 
	 * @param article 投稿者のドメイン
	 */
	public void insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);

		String sql = "INSERT INTO articles (name,content) VALUES (:name,:content)";
		template.update(sql, param);

	}

	/**
	 * 投稿情報リストのすべてを返します.
	 * 
	 * @return 投稿情報リスト
	 */
	public List<Article> findAllByList() {
		String sql = "SELECT id,name,content FROM articles ORDER BY id DESC";
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);

		return articleList;
	}

	/**
	 * 指定されたidの投稿情報を削除します
	 * 
	 * @param id 削除するid
	 */
	public void deleteById(Integer id) {
		String sql = "DELETE FROM articles WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

	/**
	 * 結合した投稿記事、コメントの情報をリストにして取得します。
	 * 
	 * @return 取得した投稿記事、コメント情報リスト
	 */
	public List<Article> findAll() {
		String sql = "select a.id as article_id, a.name as article_name, a.content as article_content,"
				+ "c.id as comment_id, c.name as comment_name, c.content as comment_content \r\n"
				+ "from articles a left outer join comments c on a.id=c.article_id ORDER BY a.id DESC, c.id DESC;";
		List<Article> articleList = template.query(sql, ARTICLE_AND_COMMENT_ROW_MAPPER);
		return articleList;
	}
}
