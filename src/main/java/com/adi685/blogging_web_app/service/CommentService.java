package com.adi685.blogging_web_app.service;

import com.adi685.blogging_web_app.DTO.CommentDTO;

public interface CommentService {

	CommentDTO addComment(CommentDTO commentDTO, Integer postId, Integer userId);

	void deleteComment(Integer id);

	CommentDTO updateComment(CommentDTO commentDTO, Integer id);

}
