package com.example.userService.feign;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalendarDto {

    private Long idx;

    private Theme theme;

    public enum Theme {

        LIGHT, DARK, HYS, JHI
    }
}
