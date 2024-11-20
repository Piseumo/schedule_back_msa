package com.example.dailyLog.dto.request;

import com.example.dailyLog.constant.Color;
import com.example.dailyLog.constant.RepeatType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ScheduleRequestInsertDto {

        @Length(min = 1, max = 50)
        private String title;

        @Length(max = 3000)
        private String content;

        private LocalDateTime start;

        private LocalDateTime end;

        private String location;

        private Color color;

        @Schema(example = "c_idx", hidden = true)
        private Long calendarIdx;

        // 반복 일정 관련 필드 추가
        private RepeatType repeatType;  // 반복 유형: NONE, DAILY, WEEKLY, MONTHLY, YEARLY

        private LocalDate repeatEndDate;  // 반복 종료 일자 (반복이 언제까지 지속되는지)


        // 날짜 유효성 검사
        @AssertTrue(message = "End time must be after the start time.")
        public boolean isEndAfterStart() {
                return end == null || start == null || !end.isBefore(start);
        }

}
