package com.backend.library.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookDTO {

    private Long id;
    private String title;
    private String genre;
    private String status;
    private Integer rating;
    private String comment;
    private Boolean favorite;
    private String coverUrl;

    private Long authorId;
}