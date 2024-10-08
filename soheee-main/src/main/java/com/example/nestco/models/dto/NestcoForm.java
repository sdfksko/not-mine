package com.example.nestco.models.dto;

import com.example.nestco.models.entity.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NestcoForm {
    private String title;  // 제목
    private String content;  // 내용
    private List<String> itemThumbnails;  // 이미지 파일
    private Long categoryId;  // 선택된 카테고리 ID
}
