package com.example.nestco.services;

import com.example.nestco.dao.repository.NestcoItemsRepository;
import com.example.nestco.models.dto.SearchKeyDTO;
import com.example.nestco.models.entity.NestcoItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContentService {

    @Autowired
    private NestcoItemsRepository nestcoItemsRepository;

    public Page<NestcoItems> nestcoItemsList(Pageable newPageable, SearchKeyDTO searchKeyDTO) {

        String searchKey = searchKeyDTO.getSearchKey();

        if(searchKey == null) {
            Page<NestcoItems> nestcoItemsList = nestcoItemsRepository.findAll(newPageable);
            return nestcoItemsList;
        } else {
            Page<NestcoItems> nestcoItemsList = nestcoItemsRepository.findAllByQuery(newPageable, searchKey);
            return nestcoItemsList;
        }
    }

    public NestcoItems delete(Long id) {
        NestcoItems nestcoItems = nestcoItemsRepository.findById(id).orElse(null);
        NestcoItems nestcoItems1 = nestcoItems;
        nestcoItemsRepository.delete(nestcoItems);
        return nestcoItems1;
    }
}
