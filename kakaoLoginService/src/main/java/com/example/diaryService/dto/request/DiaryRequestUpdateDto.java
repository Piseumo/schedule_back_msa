package com.example.diaryService.dto.request;

import com.example.diaryService.constant.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryRequestUpdateDto {

    private Long idx;

    @Length(min = 1, max = 50)
    private String title;

    @Length(max = 3000)
    private String content;

//    private LocalDate date;

    private Category category;

    private List<String> deletedImageList;

}
