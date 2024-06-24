package com.vasche.shoestore.service.impl;

import com.vasche.shoestore.domain.exception.ResourceNotFoundException;
import com.vasche.shoestore.domain.shoe.*;
import com.vasche.shoestore.repository.ShoeRepository;
import com.vasche.shoestore.service.ImageService;
import com.vasche.shoestore.web.dto.shoe.ShoeDto;
import com.vasche.shoestore.web.mappers.ShoeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoeServiceImplTest {

    @Mock
    private ShoeRepository shoeRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private ShoeMapper shoeMapper;

    @InjectMocks
    private ShoeServiceImpl shoeService;

    private ShoeDto shoeDto;
    private Shoe shoe;

    @BeforeEach
    void setUp() {
        shoeDto = new ShoeDto();
        shoeDto.setId(1L);
        shoeDto.setTitle("Title");
        shoeDto.setPrice(BigDecimal.valueOf(100));
        shoeDto.setSize(40);
        shoeDto.setDescription("Some description");
        shoeDto.setShoeModel(ShoeModel.SHOES);
        shoeDto.setSeason(Season.DEMISEASON);
        shoeDto.setSex(Sex.UNISEX);

        shoe = new Shoe();
        shoe.setId(1L);
        shoe.setTitle("Title");
        shoe.setPrice(BigDecimal.valueOf(100));
        shoe.setSize(40);
        shoe.setDescription("Some description");
        shoe.setShoeModel(ShoeModel.SHOES);
        shoe.setSeason(Season.DEMISEASON);
        shoe.setSex(Sex.UNISEX);
    }

    @Test
    void getById() {
        when(shoeRepository.findById(1L)).thenReturn(Optional.of(shoe));
        when(shoeMapper.toDto(shoe)).thenReturn(shoeDto);

        ShoeDto result = shoeService.getById(1L);

        assertEquals(shoeDto, result);
        verify(shoeRepository, times(1)).findById(1L);
        verify(shoeMapper, times(1)).toDto(shoe);
    }

    @Test
    void getByIdFailTest() {
        when(shoeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> shoeService.getById(1L));
        verify(shoeRepository, times(1)).findById(1L);
    }

    @Test
    void getAllTest() {
        when(shoeRepository.findAll()).thenReturn(List.of(shoe));
        when(shoeMapper.toDto(shoe)).thenReturn(shoeDto);

        List<ShoeDto> result = shoeService.getAll();

        assertEquals(List.of(shoeDto), result);
        verify(shoeRepository, times(1)).findAll();
        verify(shoeMapper, times(1)).toDto(shoe);
    }

    @Test
    void uploadImageTest() {
        Long id = 1L;
        String imageName = "imageName";
        ShoeImage taskImage = new ShoeImage();
        Mockito.when(imageService.upload(taskImage))
                .thenReturn(imageName);
        shoeService.uploadImage(id, taskImage);
        Mockito.verify(shoeRepository).addImage(id, imageName);
    }
}
