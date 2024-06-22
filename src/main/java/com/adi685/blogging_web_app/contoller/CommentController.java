package com.adi685.blogging_web_app.contoller;

import com.adi685.blogging_web_app.DTO.CommentDTO;
import com.adi685.blogging_web_app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.adi685.blogging_web_app.enums.Constants.DELETED_SUCCESFULLY_STRING;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/post/{postId}")
	public ResponseEntity<CommentDTO> addComment(@PathVariable Integer postId, @RequestBody CommentDTO commentDTO) {

		CommentDTO addedCommentDTO = commentService.addComment(commentDTO, postId, commentDTO.getUser().getId());
		return new ResponseEntity<>(addedCommentDTO, HttpStatus.CREATED);

	}

	@PutMapping("/{commentId}")
	public ResponseEntity<CommentDTO> updatedComment(@PathVariable Integer commentId,
			@RequestBody CommentDTO commentDTO) {
		CommentDTO updatedCommentDTO = commentService.updateComment(commentDTO, commentId);
		return new ResponseEntity<>(updatedCommentDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Integer commentId) {
		commentService.deleteComment(commentId);
		return new ResponseEntity<>(DELETED_SUCCESFULLY_STRING, HttpStatus.OK);
	}

}
