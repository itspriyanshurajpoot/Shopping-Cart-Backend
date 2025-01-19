package com.shoppingCart.service.image;

import com.shoppingCart.DTOs.ImageDTO;
import com.shoppingCart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    List<ImageDTO> addImage(List<MultipartFile> file, Long productId);
    ImageDTO updateImage(MultipartFile file, Long imageId);
    void deleteImageById(Long imageId);
    Image getImageById(Long imageId);
}
