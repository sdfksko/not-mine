package com.example.nestco.dao.repository;

import com.example.nestco.models.entity.Category;
import com.example.nestco.models.entity.Member;
import com.example.nestco.models.entity.NestcoItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NestcoItemsRepository extends JpaRepository<NestcoItems, Long> {

    // 최신 등록 상품 조회
    List<NestcoItems> findAllByOrderByCreateDateDesc();

    // 카테고리별 상품 조회
    List<NestcoItems> findByCategory(Category category);

    // 거래 완료 상태가 아닌 아이템만 조회
    List<NestcoItems> findByStatusFalse();

    //  상품명으로 상품 조회
    List<NestcoItems> findByTitleContaining(String title);

    // 회원이 업로드한 상품을 조회하는 메소드
    List<NestcoItems> findByUploader(Member uploader);

    List<NestcoItems> findByCategoryAndTitleContaining(Category category, String title);

    @Query(value = "SELECT n FROM NestcoItems AS n WHERE n.uploader.userId LIKE %:searchKey%")
    Page<NestcoItems> findAllByQuery(Pageable pageable, @Param("searchKey") String searchKey);
}
