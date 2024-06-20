package com.vasche.shoestore.service.impl;


import com.vasche.shoestore.domain.exception.ResourceNotFoundException;
import com.vasche.shoestore.domain.shoe.*;
import com.vasche.shoestore.repository.ShoeRepository;
import com.vasche.shoestore.service.ImageService;
import com.vasche.shoestore.service.ShoeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoeServiceImpl implements ShoeService {

    private final ShoeRepository shoeRepository;
    private final ImageService imageService;

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(value = "ShoeService::getById", key = "#id")
    public Shoe getById(Long id) {
        return shoeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shoe not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Shoe> getAll() {
        return shoeRepository.findAll();
    }

    @Override
    @Transactional
//    @Caching(put = @CachePut(value = "ShoeService::getById", key = "shoe.id"))
    public Shoe update(Shoe shoe) {
        if (shoe.getShoeModel() == null) {
            shoe.setShoeModel(ShoeModel.SHOES);
        }
        if (shoe.getSeason() == null) {
            shoe.setSeason(Season.DEMISEASON);
        }
        if (shoe.getSex() == null) {
            shoe.setSex(Sex.UNISEX);
        }
        shoeRepository.save(shoe);
        return shoe;
    }

    @Override
    @Transactional
//    @Caching(cacheable = @Cacheable(value = "ShoeService::getById", key = "shoe.id"))
    public Shoe create(Shoe shoe) {
        if (shoe.getShoeModel() == null) {
            shoe.setShoeModel(ShoeModel.SHOES);
        }
        if (shoe.getSeason() == null) {
            shoe.setSeason(Season.DEMISEASON);
        }
        if (shoe.getSex() == null) {
            shoe.setSex(Sex.UNISEX);
        }
        shoeRepository.save(shoe);
        return shoe;
    }

    @Override
    @Transactional
//    @CacheEvict(value = "ShoeService::getById", key = "#id")
    public void delete(Long id) {
        shoeRepository.deleteById(id);
    }

    @Override
    @Transactional
//    @CacheEvict(value = "ShoeService::getById", key = "#id")
    public void uploadImage(final Long id, final ShoeImage image) {
        String fileName = imageService.upload(image);
        shoeRepository.addImage(id, fileName);
    }
}
