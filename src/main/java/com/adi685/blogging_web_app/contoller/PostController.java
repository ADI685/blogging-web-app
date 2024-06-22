package com.adi685.blogging_web_app.contoller;

import com.adi685.blogging_web_app.DTO.ApiResponseDTO;
import com.adi685.blogging_web_app.DTO.PostDTO;
import com.adi685.blogging_web_app.DTO.PostResponseDTO;
import com.adi685.blogging_web_app.service.FileService;
import com.adi685.blogging_web_app.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.adi685.blogging_web_app.enums.Constants.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class PostController {

	private final PostService postService;

	private final FileService fileService;

	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{user_id}/category/{category_id}/post")
	private ResponseEntity<PostDTO> addPost(@PathVariable Integer user_id, @PathVariable Integer category_id,
			@RequestBody PostDTO postDTO) {
		PostDTO addedPostDto = postService.addPost(postDTO, user_id, category_id);

		return new ResponseEntity<>(addedPostDto, HttpStatus.CREATED);
	}

	@PutMapping("/post/{post_id}")
	private ResponseEntity<PostDTO> updatePost(@PathVariable Integer post_id, @RequestBody PostDTO postDTO) {
		PostDTO updatePostDTO = postService.updatePost(postDTO, post_id);

		return new ResponseEntity<>(updatePostDTO, HttpStatus.OK);
	}

	@DeleteMapping("/post/{post_id}")
	private ResponseEntity<String> deletePostById(@PathVariable Integer post_id) {
		postService.deletePostById(post_id);
		return new ResponseEntity<>(DELETED_SUCCESFULLY_STRING, HttpStatus.OK);
	}

	@GetMapping("/user/{user_id}/post")
	private ResponseEntity<List<PostDTO>> findPostByUser(@PathVariable Integer user_id) {
		List<PostDTO> postDTOList = postService.getAllPostByUser(user_id);
		return new ResponseEntity<>(postDTOList, HttpStatus.OK);
	}

	@GetMapping("/category/{category_id}/post")
	private ResponseEntity<List<PostDTO>> findPostByCategory(@PathVariable Integer category_id) {
		List<PostDTO> postDTOList = postService.getAllPostByCategory(category_id);
		return new ResponseEntity<>(postDTOList, HttpStatus.OK);
	}

	@GetMapping("/post")
	private ResponseEntity<PostResponseDTO> findAllPost(
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ASC, required = false) String sortDir) {
		PostResponseDTO postResponseDTO = postService.getAllPost(pageSize, pageNumber, sortBy, sortDir);
		return new ResponseEntity<>(postResponseDTO, HttpStatus.OK);
	}

	@GetMapping("/post/{post_id}")
	private ResponseEntity<PostDTO> findPostById(@PathVariable Integer post_id) {
		PostDTO postDTO = postService.getPostById(post_id);
		return new ResponseEntity<>(postDTO, HttpStatus.OK);
	}

	@GetMapping("/search/{keyword}/post")
	private ResponseEntity<List<PostDTO>> findAllPostByWord(@PathVariable String keyword) {
		List<PostDTO> postDTOList = postService.searchPostByKeywordTitle(keyword);
		return new ResponseEntity<>(postDTOList, HttpStatus.OK);
	}

	@GetMapping("/advanceSearch/post")
	private ResponseEntity<List<PostDTO>> findAllPostContentByWord(
			@RequestParam(value = "keyword", required = true) String keyword) {
		List<PostDTO> postDTOList = postService.searchPostByKeywordContent(keyword);
		return new ResponseEntity<>(postDTOList, HttpStatus.OK);
	}

	@PostMapping("/post/image/upload/{postId}")
	private ResponseEntity<ApiResponseDTO> uploadImageForPost(@RequestParam("image") MultipartFile file,
			@PathVariable Integer postId) {
		try {
			PostDTO updatedPost = postService.updateImage(path, file, postId);
			String messageString = String.format("Successfully updated post with postId: %d and imageName: %s", postId,
					updatedPost.getImageName());
			ApiResponseDTO apiResponseDTO = new ApiResponseDTO(messageString, SUCCESS);
			return ResponseEntity.ok(apiResponseDTO);
		}
		catch (IOException e) {
			log.error("Failed to upload image for postId: {}", postId, e);
			String messageString = String.format("Unable to upload image to post with postId: %d", postId);
			ApiResponseDTO apiResponseDTO = new ApiResponseDTO(messageString, FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponseDTO);
		}
	}

	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void getImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		InputStream inputStream = this.fileService.getResource(path, imageName);
		StreamUtils.copy(inputStream, response.getOutputStream());
	}

}
