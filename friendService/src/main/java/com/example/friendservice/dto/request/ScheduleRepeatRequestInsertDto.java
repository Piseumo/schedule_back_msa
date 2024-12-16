package com.example.friendservice.dto.request;

import com.example.friendservice.constant.RepeatType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ScheduleRepeatRequestInsertDto {
    private RepeatType repeatType;

    @Schema(name = "s_idx", hidden = true)
    private Long scheduleIdx;
}
