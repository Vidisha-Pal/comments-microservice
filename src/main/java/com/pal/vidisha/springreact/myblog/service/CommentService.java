package com.pal.vidisha.springreact.myblog.service;

import com.pal.vidisha.springreact.myblog.domain.Comment;
import com.pal.vidisha.springreact.myblog.dto.CommentDTO;
import com.pal.vidisha.springreact.myblog.dto.CommentsDTO;
import com.pal.vidisha.springreact.myblog.repository.CommentRepository;
import com.pal.vidisha.springreact.myblog.repository.specification.CommentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentSpecification commentSpecification;

    public void saveComment(CommentDTO commentDTO) {
        Comment comment = Comment
                .builder()
                .description(commentDTO.getDescription())
                .createdDate(Instant.now())
                .createdBy(commentDTO.getCreatedBy())
                .userId(commentDTO.getUserId())
                .build();
        commentRepository.save(comment);
    }

    public List<CommentDTO> getCommentsBySearchText(String searchText, Pageable paging) {
        List<CommentDTO> commentsDTO = new ArrayList<>();
        Page<Comment> comments = commentRepository.findAll(commentSpecification.commentLike(searchText), paging);
        comments.get().forEach( comment -> {
            commentsDTO.add(CommentDTO
                    .builder()
                    .id(comment.getId())
                    .userId(comment.getUserId())
                    .description(comment.getDescription())
                    .createdBy(comment.getCreatedBy())
                    .createdDate(comment.getCreatedDate())
                    .build());
        });
        return commentsDTO;
    }

    public List<CommentDTO> getAllComments(Pageable paging){
        Page<Comment> comments = commentRepository.findAll(paging);
        List<CommentDTO> commentsDTO = new ArrayList<>();


            comments.forEach( comment -> {
                commentsDTO.add(CommentDTO
                        .builder()
                        .id(comment.getId())
                        .userId(comment.getUserId())
                        .description(comment.getDescription())
                        .createdBy(comment.getCreatedBy())
                        .createdDate(comment.getCreatedDate())
                        .build());
            });


        return commentsDTO;
    }

    public CommentsDTO getCommentsByUserId(String userId, Pageable page) {
       Optional<Page<Comment>> comments = commentRepository.findAllByUserId(userId, page);


       if (comments.isPresent()) {
         return  CommentsDTO.builder()
                  .comments(
                          comments.get()
                                  .stream()
                                  .map( comment ->  CommentDTO.builder()
                                          .id(comment.getId())
                                          .description(comment.getDescription())
                                          .userId(comment.getUserId())
                                          .createdBy(comment.getCreatedBy())
                                          .createdDate(comment.getCreatedDate())
                                          .build())
                                  .collect(Collectors.toList())


                  )
                  .totalCount(comments.get().getTotalElements())
                  .pages(comments.get().getTotalPages())
                  .build();
       }
       return null;
    }


    public CommentsDTO searchUserCommentsByFilters(String userId,
                                                   String searchText,
                                                   String fromDate,
                                                   String toDate,
                                                   boolean checkIsLongTermUser,
                                                   Pageable page) {

     Page<Comment> filteredComments = commentRepository.findAll(commentSpecification.conditionalSearchForUser(
                                                                    searchText,
                                                                    fromDate,
                                                                    toDate,
                                                                    userId,
                                                                    checkIsLongTermUser),
                                                                    page);
        long totalElements = filteredComments.getTotalElements();
        int totalPages = filteredComments.getTotalPages();

        return CommentsDTO.builder()
                .comments(filteredComments.get()
                        .map(   comment ->
                                CommentDTO.builder()
                                        .userId(comment.getUserId())
                                        .description(comment.getDescription())
                                        .createdDate(comment.getCreatedDate())
                                        .createdBy(comment.getCreatedBy())
                                        .id(comment.getId())
                                        .userId(comment.getUserId())
                                        .updatedDate(comment.getUpdatedDate())
                                        .build())
                        .collect(Collectors.toList()))
                .pages(totalPages)
                .totalCount(totalElements)
                .build();
    }


}