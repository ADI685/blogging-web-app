package com.adi685.blogging_web_app.service.impl;

import com.adi685.blogging_web_app.DTO.PostDTO;
import com.adi685.blogging_web_app.DTO.PostResponseDTO;
import com.adi685.blogging_web_app.entity.Category;
import com.adi685.blogging_web_app.entity.Post;
import com.adi685.blogging_web_app.entity.User;
import com.adi685.blogging_web_app.exception.ResourceNotFoundException;
import com.adi685.blogging_web_app.repositories.CategoryRepository;
import com.adi685.blogging_web_app.repositories.PostRepository;
import com.adi685.blogging_web_app.repositories.UserRepository;
import com.adi685.blogging_web_app.service.FileService;
import com.adi685.blogging_web_app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.adi685.blogging_web_app.enums.Constants.ASC;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final ModelMapper modelMapper;

	private final PostRepository postRepository;

	private final UserRepository userRepository;

	private final CategoryRepository categoryRepository;

	private final FileService fileService;

	@Override
	public PostDTO addPost(PostDTO postDTO, Integer user_id, Integer category_id) {
		User user = userRepository.findById(user_id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user_id", user_id));
		Category category = categoryRepository.findById(category_id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category_Id", category_id));
		Post post = dtoToPost(postDTO);
		post.setUser(user);
		post.setCategory(category);
		Post savedPost = postRepository.save(post);
		return postToDTO(savedPost);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post_Id", postId));
		post.setContent(postDTO.getContent());
		post.setTitle(postDTO.getTitle());
		post.setImageName(postDTO.getImageName());
		Post updatedPost = postRepository.save(post);

		return postToDTO(updatedPost);
	}

	@Override
	public PostResponseDTO getAllPost(Integer pageSize, Integer pageNumber, String sortBy, String sortDir) {
		Sort sort = null;
		if (sortDir.equalsIgnoreCase(ASC)) {
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> page = postRepository.findAll(pageable);
		List<PostDTO> postDTOList = page.getContent().stream().map(this::postToDTO).toList();

		PostResponseDTO postResponseDTO = new PostResponseDTO();
		postResponseDTO.setPostDTOList(postDTOList);
		postResponseDTO.setLastPage(page.isLast());
		postResponseDTO.setPageNumber(page.getNumber());
		postResponseDTO.setPageSize(page.getSize());
		postResponseDTO.setTotalPages(page.getTotalPages());
		postResponseDTO.setTotalElements(page.getTotalElements());

		return postResponseDTO;
	}

	@Override
	public PostDTO getPostById(Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post_Id", postId));

		return postToDTO(post);
	}

	@Override
	public void deletePostById(Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post_Id", postId));
		postRepository.delete(post);
	}

	@Override
	public List<PostDTO> getAllPostByUser(Integer user_id) {
		User user = userRepository.findById(user_id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user_id", user_id));

		return postRepository.findByUser(user).stream().map(this::postToDTO).collect(Collectors.toList());
	}

	@Override
	public List<PostDTO> getAllPostByCategory(Integer category_id) {
		Category category = categoryRepository.findById(category_id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category_Id", category_id));

		return postRepository.findByCategory(category).stream().map(this::postToDTO).collect(Collectors.toList());
	}

	@Override
	public List<PostDTO> searchPostByKeywordTitle(String keyword) {

		return postRepository.findByTitleContaining(keyword).stream().map(this::postToDTO).collect(Collectors.toList());
	}

	@Override
	public List<PostDTO> searchPostByKeywordContent(String keyword) {

		return postRepository.searchInContent("%" + keyword + "%").stream().map(this::postToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public PostDTO updateImage(String path, MultipartFile file, Integer postId) throws IOException {
		PostDTO postDTO = this.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, file);
		postDTO.setImageName(fileName);

		return this.updatePost(postDTO, postId);
	}

	private Post dtoToPost(PostDTO postDTO) {
		return modelMapper.map(postDTO, Post.class);
	}

	private PostDTO postToDTO(Post post) {
		return modelMapper.map(post, PostDTO.class);
	}

}
