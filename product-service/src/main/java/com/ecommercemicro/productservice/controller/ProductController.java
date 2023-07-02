package com.ecommercemicro.productservice.controller;

import com.ecommercemicro.productservice.dto.BaseResponse;
import com.ecommercemicro.productservice.dto.ProductRequest;
import com.ecommercemicro.productservice.dto.ProductResponse;
import com.ecommercemicro.productservice.dto.ProductWithCategoryDto;
import com.ecommercemicro.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @GetMapping
    public BaseResponse<List<ProductResponse>> getAll(){
        List<ProductResponse> responseList = productService.getAll();

        if (!responseList.isEmpty())
            return BaseResponse.<List<ProductResponse>>builder()
                    .responseCode(1)
                    .responseDesc("success")
                    .data(responseList).build();
        return BaseResponse.<List<ProductResponse>>builder()
                .responseCode(2)
                .responseDesc("null").build();
    }

    @GetMapping("/getAllByPage")
    public BaseResponse<List<ProductResponse>> getAll(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
        List<ProductResponse> responseList = productService.getAll(pageNo - 1, pageSize);

        if (!responseList.isEmpty())
            return BaseResponse.<List<ProductResponse>>builder()
                    .responseCode(1)
                    .responseDesc("success")
                    .data(responseList).build();
        return BaseResponse.<List<ProductResponse>>builder()
                .responseCode(2)
                .responseDesc("null").build();
    }

    @GetMapping("/{id}")
    public BaseResponse<ProductResponse> getById(@PathVariable("id") Long id) {
        ProductResponse response = productService.getById(id);

        if (response != null)
            return BaseResponse.<ProductResponse>builder()
                    .responseCode(1)
                    .responseDesc("success")
                    .data(response).build();
        return BaseResponse.<ProductResponse>builder()
                .responseCode(2)
                .responseDesc("null").build();
    }

    @GetMapping("/getByProductNameContains")
    public BaseResponse<List<ProductResponse>> getByProductNameContains(@RequestParam String productName){
        List<ProductResponse> responseList = productService.getByProductNameContains(productName);

        if (!responseList.isEmpty())
            return BaseResponse.<List<ProductResponse>>builder()
                    .responseCode(1)
                    .responseDesc("success")
                    .data(responseList).build();
        return BaseResponse.<List<ProductResponse>>builder()
                .responseCode(2)
                .responseDesc("null").build();
    }

    @GetMapping("/getByCategoryIdIn")
    public BaseResponse<List<ProductResponse>> getByCaegoryIdIn(@RequestParam List<Long> categories){
        List<ProductResponse> responseList = productService.getByCategoryIdIn(categories);

        if (!responseList.isEmpty())
            return BaseResponse.<List<ProductResponse>>builder()
                    .responseCode(1)
                    .responseDesc("success")
                    .data(responseList).build();
        return BaseResponse.<List<ProductResponse>>builder()
                .responseCode(2)
                .responseDesc("null").build();
    }

    @GetMapping("/getProductWithCategoryDetails")
    public BaseResponse<List<ProductWithCategoryDto>> getProductWithCategoryDetails(){
        List<ProductWithCategoryDto> responseList = productService.getProductWithCategoryDetails();

        if (!responseList.isEmpty())
            return BaseResponse.<List<ProductWithCategoryDto>>builder()
                    .responseCode(1)
                    .responseDesc("success")
                    .data(responseList).build();
        return BaseResponse.<List<ProductWithCategoryDto>>builder()
                .responseCode(2)
                .responseDesc("null").build();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id){
        productService.deleteById(id);
    }
}
