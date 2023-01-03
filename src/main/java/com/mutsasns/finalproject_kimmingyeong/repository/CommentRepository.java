package com.mutsasns.finalproject_kimmingyeong.repository;

import com.mutsasns.finalproject_kimmingyeong.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
