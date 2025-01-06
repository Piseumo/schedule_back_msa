package com.example.calendarservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentsResponseInsertDto {

    private Long commentsIdx;

    private String cAuthor;
}
