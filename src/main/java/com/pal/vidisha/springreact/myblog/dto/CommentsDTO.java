package com.pal.vidisha.springreact.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDTO {

    private List<CommentDTO> comments;

    private long totalCount;

    private int pages;


}
