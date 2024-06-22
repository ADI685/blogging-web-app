package com.adi685.blogging_web_app.service.impl;

import com.adi685.blogging_web_app.DTO.CommentDTO;
import com.adi685.blogging_web_app.entity.Comment;
import com.adi685.blogging_web_app.entity.Post;
import com.adi685.blogging_web_app.entity.User;
import com.adi685.blogging_web_app.exception.ResourceNotFoundException;
import com.adi685.blogging_web_app.repositories.CommentRepository;
import com.adi685.blogging_web_app.repositories.PostRepository;
import com.adi685.blogging_web_app.repositories.UserRepository;
import com.adi685.blogging_web_app.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

	private final UserRepository userRepository;

	private final CommentRepository commentRepository;

	private final PostRepository postRepository;

	private final ModelMapper modelMapper;

	@Override
	public CommentDTO addComment(CommentDTO commentDTO, Integer postId, Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		Comment newComment = dtoToComment(commentDTO);
		newComment.setUser(user);
		newComment.setPost(post);
		Comment savedComment = commentRepository.save(newComment);

		return commentToDTO(savedComment);
	}

	@Override
	public void deleteComment(Integer id) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", id));
		try {
			commentRepository.delete(comment);
			log.info("Comment with id {} deleted successfully.", id);
		}
		catch (Exception e) {
			log.error("Error deleting comment with id {}: {}", id, e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public CommentDTO updateComment(CommentDTO commentDTO, Integer id) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", id));
		comment.setContent(commentDTO.getContent());

		Comment updatedComment = commentRepository.save(comment);
		return commentToDTO(updatedComment);
	}

	private Comment dtoToComment(CommentDTO commentDTO) {
		return modelMapper.map(commentDTO, Comment.class);
	}

	private CommentDTO commentToDTO(Comment comment) {
		return modelMapper.map(comment, CommentDTO.class);
	}

}
