package com.example.nestco.services;

import com.example.nestco.models.entity.ItemThumbnail;
import com.example.nestco.models.entity.NestcoItems;
import com.example.nestco.dao.repository.ItemThumbnailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(rollbackFor = {Exception.class})
@RequiredArgsConstructor
public class ItemThumbnailService {

    private final ItemThumbnailRepository itemThumbnailRepository;


    // 파일 업로드 경로를 외부 설정에서 주입받음
    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * 썸네일 등록
     */
    public void uploadThumbnail(NestcoItems items, List<MultipartFile> images) throws IOException {

            // 각 이미지 파일에 대해 업로드 및 DB 저장 수행
            for (MultipartFile image : images) {
                String dbFilePath = saveImage(image);
                ItemThumbnail thumbnail = new ItemThumbnail(items, dbFilePath);
                itemThumbnailRepository.save(thumbnail);
            }
        }

    // 이미지 파일을 저장하는 메서드
    @Transactional
    private String saveImage(MultipartFile image) throws IOException {
        // 파일 이름 생성
        String fileName = UUID.randomUUID().toString().replace("-", "")+ "_" + image.getOriginalFilename();
        // 실제 파일이 저장될 경로
        String filePath = uploadDir + File.separator + fileName;
        // DB에 저장할 경로 문자열
        String dbFilePath = "/uploads/thumbnails/" + fileName;

        Path path = Paths.get(filePath); //Path 객체 생성
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }
        Files.write(path, image.getBytes());

        return dbFilePath;
    }

    public Map<Long, String> getFirstThumbnailsForItems(List<Long> itemIds) {
        List<ItemThumbnail> thumbnails = itemThumbnailRepository.findByItems_IdIn(itemIds);
        Map<Long, String> firstThumbnailsMap = new HashMap<>();

        for (ItemThumbnail thumbnail : thumbnails) {
            Long itemId = thumbnail.getItems().getId();
            firstThumbnailsMap.putIfAbsent(itemId, thumbnail.getImagePath());
        }

        return firstThumbnailsMap;
    }

}
