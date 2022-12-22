package com.mutsasns.finalproject_kimmingyeong.repository;

import com.mutsasns.finalproject_kimmingyeong.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
