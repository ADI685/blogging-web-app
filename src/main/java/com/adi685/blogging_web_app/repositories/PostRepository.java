package com.adi685.blogging_web_app.repositories;

import com.adi685.blogging_web_app.entity.Category;
import com.adi685.blogging_web_app.entity.Post;
import com.adi685.blogging_web_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);

	List<Post> findByCategory(Category category);

	List<Post> findByTitleContaining(String keyword);

	@Query("select p from Post p where p.content like :key")
	List<Post> searchInContent(@Param("key") String keyword);

}
