package com.adi685.blogging_web_app.repositories;

import com.adi685.blogging_web_app.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
