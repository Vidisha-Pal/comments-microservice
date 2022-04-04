package com.pal.vidisha.springreact.myblog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="comment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue()
    private UUID id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String description;

    private Instant createdDate;

    @Column(nullable = false)
    private String createdBy;

    private Instant updatedDate;

}