package com.shoppingCart.service.image;

import com.shoppingCart.DTOs.ImageDTO;
import com.shoppingCart.exception.ResourceNotFoundException;
import com.shoppingCart.model.Image;
import com.shoppingCart.model.Product;
import com.shoppingCart.repository.ImageRepository;
import com.shoppingCart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<ImageDTO> addImage(List<MultipartFile> file, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exist with the product id " + productId));

        List<ImageDTO> imageDTOS = new ArrayList<>();
        for (MultipartFile f: file) {
            try {
                Image image = new Image();
                image.setFileName(f.getOriginalFilename());
                image.setFileType(f.getContentType());
                image.setImage(new SerialBlob(f.getBytes()));
                image.setProduct(product);

                String url = "/api/v1/images/download/";

                Image saveImage = imageRepository.save(image);

                image.setDownloadUrl(url + saveImage.getImageId());
                Image saveImage1 = imageRepository.save(image);

                ImageDTO savedImageDto = new ImageDTO();
                savedImageDto.setImageId(saveImage1.getImageId());
                savedImageDto.setFileName(saveImage1.getFileName());
                savedImageDto.setDownloadUrl(saveImage1.getDownloadUrl());

                imageDTOS.add(savedImageDto);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

        }
        return imageDTOS;
    }

    @Override
    public ImageDTO updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);

        try {
            image.setImage(new SerialBlob(file.getBytes()));
            image.setFileType(file.getContentType());
            image.setFileName(file.getOriginalFilename());
            return imageToDto(imageRepository.save(image));

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteImageById(Long imageId) {
        imageRepository.findById(imageId).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ResourceNotFoundException("Image doesn't exist with image id " + imageId);
        });
    }

    @Override
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException("Image Image doesn't exist with image id " + imageId));
    }

    // dto to image
    public Image dtoToImage(ImageDTO dto){
        return modelMapper.map(dto, Image.class);
    }

    // image to dto
    public ImageDTO imageToDto(Image image){
        return modelMapper.map(image, ImageDTO.class);
    }
}
