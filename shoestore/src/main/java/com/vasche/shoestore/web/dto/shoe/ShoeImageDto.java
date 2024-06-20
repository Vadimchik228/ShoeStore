package com.vasche.shoestore.web.dto.shoe;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class ShoeImageDto {

    @NotNull(message = "Image must be not null.")
    private MultipartFile file;
}
