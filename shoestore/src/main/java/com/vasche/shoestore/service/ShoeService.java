package com.vasche.shoestore.service;

import com.vasche.shoestore.domain.shoe.Shoe;

import java.util.List;

public interface ShoeService {

    Shoe getById(Long id);

    List<Shoe> getAll();

    Shoe update(Shoe shoe);

    Shoe create(Shoe shoe);

    void delete(Long id);

}
