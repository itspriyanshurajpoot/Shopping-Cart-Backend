package com.shoppingCart.service.Product;

import com.shoppingCart.DTOs.ImageDTO;
import com.shoppingCart.DTOs.ProductDTO;
import com.shoppingCart.DTOs.ProductInputDTO;
import com.shoppingCart.exception.ProductNotFoundException;
import com.shoppingCart.exception.ResourceNotFoundException;
import com.shoppingCart.model.Category;
import com.shoppingCart.model.Image;
import com.shoppingCart.model.Product;
import com.shoppingCart.repository.CategoryRepository;
import com.shoppingCart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    // product repository object
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    @Override
    public ProductDTO addProduct(ProductInputDTO request) {
        // check the category exist or not , if yes the set it else create new category

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategoryName()))
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(request.getCategoryName());
                    return newCategory;
                } );

        Product product = inputDtoToProduct(request);
        product.setBrand(request.getBrand().toUpperCase());
        product.setCategory(category);
        return productToDto(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.findById(productId).ifPresentOrElse(productRepository::delete,
                () -> {throw new ResourceNotFoundException("Product doesn't exist");});
    }

    @Override
    public ProductDTO updateProduct(ProductInputDTO request, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product doesn't exist"));

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategoryName()))
                        .orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist"));

        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setInventory(request.getInventory());
        product.setCategory(category);
        return productToDto(productRepository.save(product));
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exist"));
        return productToDto(product);
    }

    @Override
    public List<ProductDTO> getAllProduct() {
        List<Product> allProduct = productRepository.findAll();
        List<ProductDTO> allProductDto = allProduct.stream().map(this::productToDto).toList();
        return allProductDto;
    }

    @Override
    public List<ProductDTO> getAllProductByBrand(String brand) {
        List<Product> allProduct = productRepository.findByBrand(brand);
        List<ProductDTO> allProductDto = allProduct.stream().map(this::productToDto).toList();
        return allProductDto;
    }

    @Override
    public List<ProductDTO> getAllProductByCategoryName(String category) {
        List<Product> allProduct = productRepository.findByCategoryName(category);
        List<ProductDTO> allProductDto = allProduct.stream().map(this::productToDto).toList();
        return allProductDto;
    }

    @Override
    public List<ProductDTO> getAllProductByCategoryNameAndBrand(String category, String brand) {
        List<Product> allProduct = productRepository.findByCategoryNameAndBrand(category, brand);
        List<ProductDTO> allProductDto = allProduct.stream().map(this::productToDto).toList();
        return allProductDto;
    }

    @Override
    public List<ProductDTO> getProductByName(String name) {
        List<Product> allProduct = productRepository.findByName(name);
        List<ProductDTO> allProductDto = allProduct.stream().map(this::productToDto).toList();
        return allProductDto;
    }

    @Override
    public List<ProductDTO> getProductByBrandAndName(String brand, String name) {
        List<Product> allProduct = productRepository.findByBrandAndName(brand, name);
        List<ProductDTO> allProductDto = allProduct.stream().map(this::productToDto).toList();

        return allProductDto;
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    // dto to product
    private Product dtoToProduct(ProductDTO request){
        return modelMapper.map(request, Product.class);
    }

    // product to dto
    public ProductDTO productToDto(Product product){
        ProductDTO dto = modelMapper.map(product, ProductDTO.class);
        List<Image> images = product.getImage();
        List<ImageDTO> imageDTOS = images.stream().map(image -> modelMapper.map(image, ImageDTO.class))
                .toList();

        dto.setImage(imageDTOS);
        return dto;
    }

    // input dto to product
    private Product inputDtoToProduct(ProductInputDTO dto){
        Product product = new Product();
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setInventory(dto.getInventory());

        return product;
    }
}
