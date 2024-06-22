package com.adi685.blogging_web_app.repositories;

import com.adi685.blogging_web_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

}
