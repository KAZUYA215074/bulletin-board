package com.example.form;

import javax.validation.constraints.NotBlank;

/**
 * コメント投稿のフォーム.
 * 
 * @author kazuya.makida
 *
 */
public class CommentForm {
	/** 名前 */
	@NotBlank
	private String name;
	/** コメント */
	@NotBlank
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
