package com.duncantait.blog.repository;

import com.duncantait.blog.repository.model.PersistentBlogPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BlogPostRepositoryImpl implements BlogPostCriteriaQueries {

    private final EntityManager em;

    @Override
    public List<PersistentBlogPost> findByTagsWithCriteriaQuery(Collection<String> tags) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PersistentBlogPost> query = cb.createQuery(PersistentBlogPost.class);
        Root<PersistentBlogPost> blogPost = query.from(PersistentBlogPost.class);
        Join<Object, Object> tags2 = blogPost.join("tags");
        query.where(tags2.get("name").in(tags));
        TypedQuery<PersistentBlogPost> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
