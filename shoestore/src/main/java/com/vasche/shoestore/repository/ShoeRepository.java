package com.vasche.shoestore.repository;

import com.vasche.shoestore.domain.shoe.Shoe;

import java.util.List;
import java.util.Optional;

public interface ShoeRepository {
    Optional<Shoe> findById(Long id);

    List<Shoe> findAll();

    void update(Shoe shoe);

    void create(Shoe shoe);

    void delete(Long id);
}
