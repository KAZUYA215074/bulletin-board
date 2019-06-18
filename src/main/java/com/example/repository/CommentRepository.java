package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * コメントのリポジトリです.
 * 
 * @author kazuya.makida
 *
 */
@Repository
public class CommentRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};

	/**
	 * コメントを追加します.
	 * 
	 * @param comment
	 */
	public void insert(Comment comment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		String sql = "INSERT INTO comments (name,content,article_id) VALUES (:name,:content,:articleId)";

		template.update(sql, param);
	}

	/**
	 * 記事に対応したコメントリストを取得します
	 * 
	 * @return コメントリスト
	 */
	public List<Comment> findByArticleId(Integer id) {
		String sql = "SELECT id,name,content,article_id FROM comments WHERE article_id=:id ORDER BY id DESC";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);

		return commentList;
	}
	
	/**
	 * 記事idでコメントを消します.
	 * 
	 * @param id 削除した記事id
	 */
	public void deleteByArticleId(Integer id) {
		String sql = "DELETE FROM comments WHERE article_id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
