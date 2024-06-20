package com.vasche.shoestore.service;

import com.vasche.shoestore.domain.shoe.Shoe;
import com.vasche.shoestore.domain.shoe.ShoeImage;

import java.util.List;

public interface ShoeService {

    Shoe getById(Long id);

    List<Shoe> getAll();

    Shoe update(Shoe shoe);

    Shoe create(Shoe shoe);

    void delete(Long id);

    void uploadImage(Long id, ShoeImage image);

}
