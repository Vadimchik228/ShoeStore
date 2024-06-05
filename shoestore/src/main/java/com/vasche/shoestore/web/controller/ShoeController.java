package com.vasche.shoestore.web.controller;

import com.vasche.shoestore.domain.shoe.Shoe;
import com.vasche.shoestore.service.ShoeService;
import com.vasche.shoestore.web.dto.shoe.ShoeDto;
import com.vasche.shoestore.web.dto.validation.OnCreate;
import com.vasche.shoestore.web.dto.validation.OnUpdate;
import com.vasche.shoestore.web.mappers.ShoeMapper;
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
    private final ShoeMapper shoeMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Get shoe by id")
    public ShoeDto getById(@PathVariable Long id) {
        Shoe shoe = shoeService.getById(id);
        return shoeMapper.toDto(shoe);
    }

    @GetMapping
    @Operation(summary = "Get all shoes")
    public List<ShoeDto> getAllShoes() {
        List<Shoe> shoes = shoeService.getAll();
        return shoeMapper.toDto(shoes);
    }

    @PostMapping
    @Operation(summary = "Create shoe")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public ShoeDto create(@Validated(OnCreate.class) @RequestBody ShoeDto dto) {
        Shoe shoe = shoeMapper.toEntity(dto);
        Shoe createdShoe = shoeService.create(shoe);
        return shoeMapper.toDto(createdShoe);
    }

    @PutMapping
    @Operation(summary = "Update shoe")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public ShoeDto update(@Validated(OnUpdate.class) @RequestBody ShoeDto dto) {
        Shoe shoe = shoeMapper.toEntity(dto);
        Shoe updatedShoe = shoeService.update(shoe);
        return shoeMapper.toDto(updatedShoe);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete shoe by id")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public void deleteById(@PathVariable Long id) {
        shoeService.delete(id);
    }

}