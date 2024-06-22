package com.adi685.blogging_web_app.repositories;

import com.adi685.blogging_web_app.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
