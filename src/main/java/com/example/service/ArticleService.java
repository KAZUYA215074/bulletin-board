package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Article;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

/**
 * 投稿者のサービスです.
 * 
 * @author kazuya.makida
 *
 */
@Service
@Transactional
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private CommentRepository commentRepository;

	/**
	 * 投稿情報をリポジトリに送ります.
	 * 
	 * @param article 入力された投稿情報
	 */
	public void add(Article article) {
		articleRepository.insert(article);
	}

	/**
	 * 投稿情報リストをコントローラーに渡す.
	 * 
	 * @return 投稿情報リスト
	 */
	public List<Article> findAllByList() {
		return articleRepository.findAllByList();
	}

	/**
	 * 削除するidをリポジトリに送る.
	 * 
	 * @param id 削除するid
	 */
	public void deleteArticleAndComment(Integer id) {
		commentRepository.deleteByArticleId(id);
		articleRepository.deleteById(id);
	}

	/**
	 * 取得した投稿記事、コメント情報のリストをコントローラーに渡します.
	 * 
	 * @return 投稿記事、コメント情報リスト
	 */
	public List<Article> findAll() {
		return articleRepository.findAll();
	}
}
