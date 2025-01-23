package com.shoppingCart.controllers;

import com.shoppingCart.DTOs.ImageDTO;
import com.shoppingCart.model.Image;
import com.shoppingCart.response.ApiResponse;
import com.shoppingCart.service.image.IImageService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService imageService;

    // upload image
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadImage(@RequestParam List<MultipartFile> file,@RequestParam Long productId){
        try{
            List<ImageDTO> imageDTOS = imageService.addImage(file, productId);
            return ResponseEntity.ok(new ApiResponse("Upload success!", imageDTOS));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    // download image
    @GetMapping("/download/{imageId}")
    public ResponseEntity<?> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int)image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }


    // update Image
    @PutMapping("/update/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestParam MultipartFile file){
        return new ResponseEntity<>(new ApiResponse("Update successfully", imageService.updateImage(file, imageId)), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
        imageService.deleteImageById(imageId);
        return new ResponseEntity<>(new ApiResponse("Delete successful", null), HttpStatus.NO_CONTENT);
    }
}
