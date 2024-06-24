package com.vasche.shoestore.web.controller;

import com.vasche.shoestore.domain.shoe.ShoeImage;
import com.vasche.shoestore.service.ShoeService;
import com.vasche.shoestore.web.dto.shoe.ShoeDto;
import com.vasche.shoestore.web.dto.shoe.ShoeImageDto;
import com.vasche.shoestore.web.dto.validation.OnCreate;
import com.vasche.shoestore.web.dto.validation.OnUpdate;
import com.vasche.shoestore.web.mappers.ShoeImageMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shoes")
@RequiredArgsConstructor
@Validated
@Tag(name = "Shoe Controller", description = "Shoe API")
public class ShoeController {

    private final ShoeService shoeService;
    private final ShoeImageMapper shoeImageMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Get shoe by id")
    public ShoeDto getById(@PathVariable Long id) {
        return shoeService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Get all shoes")
    public List<ShoeDto> getAllShoes() {
        return shoeService.getAll();
    }

    @PostMapping
    @Operation(summary = "Create shoe")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public ShoeDto create(@Validated(OnCreate.class) @RequestBody ShoeDto dto) {
        return shoeService.create(dto);
    }

    @PutMapping
    @Operation(summary = "Update shoe")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public ShoeDto update(@Validated(OnUpdate.class) @RequestBody ShoeDto dto) {
        return shoeService.update(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete shoe by id")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public void deleteById(@PathVariable Long id) {
        shoeService.delete(id);
    }

    @PostMapping("/{id}/image")
    @Operation(summary = "Upload image to shoe")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public void uploadImage(@PathVariable Long id,
                            @Validated @ModelAttribute ShoeImageDto imageDto) {
        ShoeImage image = shoeImageMapper.toEntity(imageDto);
        shoeService.uploadImage(id, image);
    }

}