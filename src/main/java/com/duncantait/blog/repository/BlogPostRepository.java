package com.duncantait.blog.repository;

import com.duncantait.blog.repository.model.PersistentBlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<PersistentBlogPost, Long> {

    @Query("SELECT s FROM PersistentBlogPost s JOIN s.tags t WHERE t.name IN :tags")
    List<PersistentBlogPost> findAllByTags(Collection<String> tags);
}
