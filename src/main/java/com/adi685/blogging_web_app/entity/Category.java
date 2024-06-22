package com.adi685.blogging_web_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@NoArgsConstructor
@Getter
@Setter
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer categoryId;

	@Column(name = "title", unique = true)
	private String categoryTitle;

	@Column(name = "description")
	private String categoryDescription;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> post = new ArrayList<>();

}
