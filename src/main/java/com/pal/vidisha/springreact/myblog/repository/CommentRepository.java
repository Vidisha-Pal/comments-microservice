package com.pal.vidisha.springreact.myblog.repository;

import com.pal.vidisha.springreact.myblog.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends
        JpaRepository <Comment, UUID>,
        JpaSpecificationExecutor<Comment> {

    Optional<Page<Comment>> findAllByUserId(String userId, Pageable page);

    Page<Comment> findAll(Specification<Comment> specification, Pageable page);

    List<Comment> findAll(Specification<Comment> specification);

    Page<Comment> findAll( Pageable page);

    List<Comment> findAll();

}
