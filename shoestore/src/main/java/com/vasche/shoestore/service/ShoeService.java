package com.vasche.shoestore.service;

import com.vasche.shoestore.domain.shoe.ShoeImage;
import com.vasche.shoestore.web.dto.shoe.ShoeDto;

import java.util.List;

public interface ShoeService {

    ShoeDto getById(Long id);

    List<ShoeDto> getAll();

    ShoeDto update(ShoeDto shoeDto);

    ShoeDto create(ShoeDto shoeDto);

    void delete(Long id);

    void uploadImage(Long id, ShoeImage image);

}
