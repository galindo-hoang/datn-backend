package com.example.backendservice.controller;

import com.example.backendservice.common.controller.BaseController;
import com.example.backendservice.model.dto.CategoryDto;
import com.example.backendservice.model.request.CategoryRequest;
import com.example.backendservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController extends BaseController {
    private final CategoryService categoryService;

    @RequestMapping(path = "add", method = POST)
    ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryRequest categoryRequest) {
        System.out.println(categoryRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(categoryService.addCategory(categoryRequest));
    }

    @RequestMapping(path = "update", method = POST)
    ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(categoryService.updateCategory(categoryRequest));
    }

    @RequestMapping(path = "delete", method = DELETE)
    ResponseEntity<Object> removeCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.removeCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping()
    ResponseEntity<CategoryDto> getDetails(@RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryService.findDetailsCategory(id));
    }

    @GetMapping(path = "multiple")
    ResponseEntity<List<CategoryDto>> getList(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "0") Long offSet,
            @RequestParam(required = false, defaultValue = "20") Long size) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findCategoriesByName(name, offSet, size));
    }

    @GetMapping(path = "size")
    ResponseEntity<Long> getSize(
            @RequestParam(required = false, defaultValue = "") String text
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getSize(text));
    }
}
