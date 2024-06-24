package com.vasche.shoestore.service.impl;


import com.vasche.shoestore.domain.exception.ResourceNotFoundException;
import com.vasche.shoestore.domain.shoe.*;
import com.vasche.shoestore.repository.ShoeRepository;
import com.vasche.shoestore.service.ImageService;
import com.vasche.shoestore.service.ShoeService;
import com.vasche.shoestore.web.dto.shoe.ShoeDto;
import com.vasche.shoestore.web.mappers.ShoeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoeServiceImpl implements ShoeService {

    private final ShoeRepository shoeRepository;
    private final ImageService imageService;
    private final ShoeMapper shoeMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "ShoeService::getById", key = "#id")
    public ShoeDto getById(Long id) {
        Optional<Shoe> shoe = shoeRepository.findById(id);
        if (shoe.isPresent()) {
            return shoeMapper.toDto(shoe.get());
        } else {
            throw new ResourceNotFoundException("Shoe not found.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShoeDto> getAll() {
        return shoeRepository.findAll()
                .stream()
                .map(shoeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CachePut(value = "ShoeService::getById", key = "#shoeDto.id")
    public ShoeDto update(ShoeDto shoeDto) {
        if (shoeDto.getShoeModel() == null) {
            shoeDto.setShoeModel(ShoeModel.SHOES);
        }
        if (shoeDto.getSeason() == null) {
            shoeDto.setSeason(Season.DEMISEASON);
        }
        if (shoeDto.getSex() == null) {
            shoeDto.setSex(Sex.UNISEX);
        }
        shoeRepository.save(shoeMapper.toEntity(shoeDto));
        return shoeDto;
    }

    @Override
    @Transactional
    @CachePut(value = "ShoeService::getById", key = "#result.id", condition = "#result != null")
    public ShoeDto create(ShoeDto shoeDto) {
        if (shoeDto.getShoeModel() == null) {
            shoeDto.setShoeModel(ShoeModel.SHOES);
        }
        if (shoeDto.getSeason() == null) {
            shoeDto.setSeason(Season.DEMISEASON);
        }
        if (shoeDto.getSex() == null) {
            shoeDto.setSex(Sex.UNISEX);
        }
        var shoe = shoeRepository.save(shoeMapper.toEntity(shoeDto));
        shoeDto.setId(shoe.getId());
        return shoeDto;
    }

    @Override
    @Transactional
    @CacheEvict(value = "ShoeService::getById", key = "#id")
    public void delete(Long id) {
        shoeRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "ShoeService::getById", key = "#id")
    public void uploadImage(final Long id, final ShoeImage image) {
        String fileName = imageService.upload(image);
        shoeRepository.addImage(id, fileName);
    }
}
