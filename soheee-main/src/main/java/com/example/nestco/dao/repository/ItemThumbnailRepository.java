package com.example.nestco.dao.repository;

import com.example.nestco.models.entity.ItemThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ItemThumbnailRepository extends JpaRepository<ItemThumbnail, Long> {
    List<ItemThumbnail> findByItems_IdIn(List<Long> itemIds);


}
