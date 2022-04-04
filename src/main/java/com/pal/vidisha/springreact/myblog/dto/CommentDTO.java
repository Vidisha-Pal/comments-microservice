package com.pal.vidisha.springreact.myblog.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class CommentDTO {

    private UUID id;

    private String userId;

    @NotNull(message = "comment cannot be empty")
    private String description;

    private Instant createdDate;

    private Instant updatedDate;

    private String createdBy;

}
