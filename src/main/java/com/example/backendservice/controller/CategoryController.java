package com.example.backendservice.controller;

import com.example.backendservice.common.controller.BaseController;
import com.example.backendservice.model.dto.CategoryDto;
import com.example.backendservice.model.request.CategoryRequest;
import com.example.backendservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController extends BaseController {
    private final CategoryService categoryService;

    @RequestMapping(name = "add", method = POST)
    ResponseEntity<CategoryDto> addCategory(CategoryRequest categoryRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(categoryService.addCategory(categoryRequest));
    }

    @RequestMapping(name = "delete", method = DELETE)
    ResponseEntity<Object> removeCategory(CategoryRequest categoryRequest) {
        categoryService.removeCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping()
    ResponseEntity<CategoryDto> getDetails(@RequestParam Long id, @RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryService.findDetailsCategory(
                        Map.of("id", id,
                                "name", name
                        )
                ));
    }

    @GetMapping()
    ResponseEntity<List<CategoryDto>> getList(@RequestParam Long offSet, @RequestParam Long limit) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findListCategories(offSet, limit));
    }
}
