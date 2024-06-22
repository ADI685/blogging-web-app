package com.adi685.blogging_web_app.service;

import com.adi685.blogging_web_app.DTO.PostDTO;
import com.adi685.blogging_web_app.DTO.PostResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {

	PostDTO addPost(PostDTO postDTO, Integer user_id, Integer category_id);

	PostDTO updatePost(PostDTO postDTO, Integer postId);

	PostResponseDTO getAllPost(Integer pageSize, Integer pageNumber, String sortBy, String sortDir);

	PostDTO getPostById(Integer id);

	void deletePostById(Integer id);

	List<PostDTO> getAllPostByUser(Integer user_id);

	List<PostDTO> getAllPostByCategory(Integer category_id);

	List<PostDTO> searchPostByKeywordTitle(String keyword);

	List<PostDTO> searchPostByKeywordContent(String keyword);

	PostDTO updateImage(String path, MultipartFile file, Integer postId) throws IOException;

}
