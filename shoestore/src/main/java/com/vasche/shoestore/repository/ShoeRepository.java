package com.vasche.shoestore.repository;


import com.vasche.shoestore.domain.shoe.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoeRepository extends JpaRepository<Shoe, Long> {

    @Modifying
    @Query(value = """
            INSERT INTO shoestore.shoes_images (shoe_id, image)
            VALUES (:id, :fileName)
            """, nativeQuery = true)
    void addImage(
            @Param("id") Long id,
            @Param("fileName") String fileName
    );
}
