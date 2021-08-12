package com.duncantait.blog.repository;

import com.duncantait.blog.repository.model.PersistentBlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

public interface BlogPostCriteriaQueries  {
    List<PersistentBlogPost> findByTagsWithCriteriaQuery(Collection<String> tags);
}
