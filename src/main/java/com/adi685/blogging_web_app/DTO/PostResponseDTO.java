package com.adi685.blogging_web_app.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostResponseDTO {

	private List<PostDTO> postDTOList;

	private int pageNumber;

	private int pageSize;

	private long totalElements;

	private int totalPages;

	private boolean isLastPage;

}
