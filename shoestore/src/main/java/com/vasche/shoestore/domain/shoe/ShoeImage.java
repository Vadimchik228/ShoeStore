package com.vasche.shoestore.domain.shoe;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ShoeImage {

    private MultipartFile file;
}
