package com.example.calendarservice.dto.response;

import com.example.calendarservice.constant.Color;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Builder
@Getter
public class ScheduleResponseYearDto {

        private LocalDateTime start;

        private Color color;

}
