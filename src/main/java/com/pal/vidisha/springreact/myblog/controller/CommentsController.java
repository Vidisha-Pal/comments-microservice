package com.pal.vidisha.springreact.myblog.controller;

import com.pal.vidisha.springreact.myblog.dto.CommentDTO;
import com.pal.vidisha.springreact.myblog.dto.CommentsDTO;
import com.pal.vidisha.springreact.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "http://localhost:3000/")
public class CommentsController {


    @Autowired
    CommentService commentService;

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE ,
                    MediaType.APPLICATION_XML_VALUE},
            produces= {
                    MediaType.APPLICATION_JSON_VALUE ,
                    MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO) throws Exception {
        commentDTO.setCreatedDate(Instant.now());
        commentService.saveComment(commentDTO);
        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }

    @GetMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE ,
                    MediaType.APPLICATION_XML_VALUE},
            produces= {
                    MediaType.APPLICATION_JSON_VALUE ,
                    MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity<List<CommentDTO>> getComments(
            @RequestParam(defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(defaultValue = "0", required = false) Integer page
    ) throws Exception {

        Pageable paging  = PageRequest.of(page, pageSize);
        List<CommentDTO> commentsDTO = commentService.getAllComments(paging);
        return new ResponseEntity<>(commentsDTO, HttpStatus.CREATED);
    }

    @GetMapping(
            path = "/user/{userId}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE ,
                    MediaType.APPLICATION_XML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity<CommentsDTO> getCommentsByUser(
            @PathVariable String userId,
            @RequestParam(defaultValue= "0", required = false) Integer page ,
            @RequestParam(defaultValue= "5", required = false) Integer pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);
       CommentsDTO commentsDTOs = commentService.getCommentsByUserId(userId, paging);
        return new ResponseEntity<>( commentsDTOs, HttpStatus.ACCEPTED);
    }

    @GetMapping(
            path = "/search",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE ,
                    MediaType.APPLICATION_XML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity< CommentsDTO> searchUserCommentsByFilters(
            @RequestParam(defaultValue= "0", required = false) Integer page ,
            @RequestParam(defaultValue= "5", required = false) Integer pageSize,
            @RequestParam(  required = true) String userId,
            @RequestParam( defaultValue= "", required = false) String searchText,
            @RequestParam( defaultValue= "", required = false) String fromDate,
            @RequestParam( defaultValue= "", required = false) String toDate,
            @RequestParam( defaultValue= "false", required = false) String checkIsLongTermUser) {
        Pageable paging = PageRequest.of(page, pageSize);

        CommentsDTO commentsDTOs = commentService.searchUserCommentsByFilters(
                userId,
                searchText,
                fromDate,
                toDate,
                Boolean.valueOf(checkIsLongTermUser),
                paging);
        return new ResponseEntity<>( commentsDTOs, HttpStatus.ACCEPTED);
    }


}
