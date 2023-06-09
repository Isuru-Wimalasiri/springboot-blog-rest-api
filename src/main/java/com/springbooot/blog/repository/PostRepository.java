package com.springbooot.blog.repository;

import com.springbooot.blog.entity.Comment;
import com.springbooot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

}
