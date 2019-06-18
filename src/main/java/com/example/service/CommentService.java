package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Comment;
import com.example.repository.CommentRepository;

/**
 * コメントのリポジトリです
 * 
 * @author kazuya.makida
 *
 */
@Service
@Transactional
public class CommentService {
	@Autowired
	private CommentRepository repository;
	
	
	/**
	 * コメント情報をリポジトリに送ります.
	 * 
	 */
	public void add(Comment comment) {
		repository.insert(comment);
	}
	
	/**
	 * コメントのリストの返します
	 * 
	 * @return コメントリスト
	 */
	public List<Comment> findByArticleId(Integer id){
		return repository.findByArticleId(id);
	}
	
	public void deleteByArticleId(Integer id) {
		repository.deleteByArticleId(id);
	}
}
