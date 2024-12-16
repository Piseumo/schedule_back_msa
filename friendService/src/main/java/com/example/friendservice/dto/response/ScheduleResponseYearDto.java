package com.example.friendservice.dto.response;

import com.example.friendservice.constant.Color;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Builder
@Getter
public class ScheduleResponseYearDto {

        private LocalDateTime start;

        private Color color;

}
