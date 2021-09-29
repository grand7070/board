package com.board.board.repository;

import com.board.board.domain.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}

/*
    조회는 querydsl 로, 등록/수정/삭제는 Spring Data JPA 로.
 */
