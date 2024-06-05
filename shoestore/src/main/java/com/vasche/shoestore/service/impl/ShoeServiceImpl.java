package com.vasche.shoestore.service.impl;


import com.vasche.shoestore.domain.exception.ResourceNotFoundException;
import com.vasche.shoestore.domain.shoe.Season;
import com.vasche.shoestore.domain.shoe.Sex;
import com.vasche.shoestore.domain.shoe.Shoe;
import com.vasche.shoestore.domain.shoe.ShoeModel;
import com.vasche.shoestore.repository.ShoeRepository;
import com.vasche.shoestore.service.ShoeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoeServiceImpl implements ShoeService {

    private final ShoeRepository shoeRepository;

    @Override
    @Transactional(readOnly = true)
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
        shoeRepository.update(shoe);
        return shoe;
    }

    @Override
    @Transactional
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
        shoeRepository.create(shoe);
        return shoe;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        shoeRepository.delete(id);
    }
}
