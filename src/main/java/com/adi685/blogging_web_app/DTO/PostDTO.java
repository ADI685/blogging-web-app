package com.adi685.blogging_web_app.DTO;

import com.adi685.blogging_web_app.entity.Category;
import com.adi685.blogging_web_app.entity.Comment;
import com.adi685.blogging_web_app.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

	private Integer id;

	private String title;

	private String content;

	private String imageName;

	private Date createDate;

	private Date modifyDate;

	private UserDTO user;

	private CategoryDTO category;

	private Set<CommentDTO> comments = new HashSet<>();

	// Note: we will take user_id and category_id in url

}
