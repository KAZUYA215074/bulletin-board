package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.service.ArticleService;
import com.example.service.CommentService;

/**
 * 掲示板コントローラー
 * 
 * @author kazuya.makida
 *
 */
@Controller
@RequestMapping("/bulletin-board")
public class BulletinBoardController {

	@Autowired
	private ArticleService articaleService;
	@Autowired
	private CommentService commentService;
	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}

	/**
	 * 掲示板を表示させます
	 * 
	 * @param postForm
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String index2(Model model) {

		List<Article> articleList = articaleService.findAllByList();

		for (Article post : articleList) {
			List<Comment> commentList = commentService.findByArticleId(post.getId());
			post.setCommentList(commentList);
		}

		model.addAttribute("articleList", articleList);

		return "bulletin-board-list";
	}

	@RequestMapping("")
	public String index(Model model) {

		List<Article> articleList = articaleService.findAll();
		model.addAttribute("articleList", articleList);
		return "bulletin-board";
	}

	/**
	 * 記事を投稿する.
	 * 
	 * @param articleForm 投稿情報
	 * @param result      エラー情報
	 * @param model       リクエストスコープ
	 * @return indexにリダイレクト
	 */
	@RequestMapping("/add")
	public String addAtricle(@Validated ArticleForm articleForm, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:/bulletin-board";
		}

		Article article = new Article();
		BeanUtils.copyProperties(articleForm, article);
		articaleService.add(article);

		return "redirect:/bulletin-board";
	}

	/**
	 * 投稿削除を行います.
	 * 
	 * @param id 削除するid
	 * @return indexにリダイレクトします
	 */
	@RequestMapping("/delete")
	public String delete(String id) {

		articaleService.deleteArticleAndComment(Integer.parseInt(id));

		return "redirect:/bulletin-board";
	}

	/**
	 * コメントを追加します.
	 * 
	 * @param commentForm 入力されたコメント
	 * @param result      エラー情報
	 * @param id          対象の記事id
	 * @param attr        リダイレクト先に渡す
	 * @return メイン画面へ
	 */
	@RequestMapping("/comment")
	public String addComent(@Validated CommentForm commentForm, BindingResult result, String id,
			RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "redirect:/bulletin-board";
		}
		
		Comment comment = new Comment();
		BeanUtils.copyProperties(commentForm, comment);
		comment.setArticleId(Integer.parseInt(id));

		commentService.add(comment);
		
		return "redirect:/bulletin-board";
	}
}
