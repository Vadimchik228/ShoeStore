package com.vasche.shoestore.repository;

import com.vasche.shoestore.domain.shoe.Shoe;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ShoeRepositoryTest extends RepositoryTestBase {

    @Test
    void saveShoeTest() {
        Shoe shoe = getShoe("Title");

        Shoe savedShoe = shoeRepository.save(shoe);

        assertThat(savedShoe).isEqualTo(shoe);
    }

    @Test
    void findAllTest() {
        Shoe shoe1 = getShoe("Title 1");

        Shoe shoe2 = getShoe("Title 2");

        shoeRepository.saveAll(List.of(shoe1, shoe2));

        List<Shoe> shoes = shoeRepository.findAll();

        assertThat(shoes).hasSize(2);
    }

    @Test
    void addImageTest() {
        String imageName = "image.jpg";

        Shoe shoe = getShoe("Title");

        shoe.setImages(List.of(imageName));

        shoeRepository.save(shoe);

        shoeRepository.addImage(shoe.getId(), imageName);

        Shoe updatedShoe = shoeRepository.findById(shoe.getId()).orElseThrow();

        assertThat(updatedShoe.getImages()).isNotEmpty();
    }

}
