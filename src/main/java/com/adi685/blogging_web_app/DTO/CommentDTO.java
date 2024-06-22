package com.adi685.blogging_web_app.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {

	private Integer id;

	private String content;

	private Date createDate;

	private Date modifyDate;

	private UserDTO user;

}
