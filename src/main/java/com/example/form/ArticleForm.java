package com.example.form;

import javax.validation.constraints.NotBlank;

/**
 * 投稿者入力フォーム
 * 
 * @author kazuya.makida
 *
 */
public class ArticleForm {

	/* 投稿者名 */
	@NotBlank(message="※投稿者名を入力してください")
	private String name;
	/* 投稿内容 */
	@NotBlank(message="※投稿内容を入力してください")
	private String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
