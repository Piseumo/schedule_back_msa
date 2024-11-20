package com.example.dailyLog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExchangeDiaryResponseSummaryDto {
    private Long diaryId;
    private String groupName;
    private List<String> participants;

}
