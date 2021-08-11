package com.duncantait.blog.repository;

import com.duncantait.blog.repository.model.PersistentComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogCommentRepository extends JpaRepository<PersistentComment, Long> {

}
