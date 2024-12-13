package com.example.userservice.feign;

import com.example.userservice.constant.Theme;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalendarDto {

    private Long idx;

    private Theme theme;
}
