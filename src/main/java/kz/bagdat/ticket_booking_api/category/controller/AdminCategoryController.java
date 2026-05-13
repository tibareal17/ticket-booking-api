package kz.bagdat.ticket_booking_api.category.controller;

import jakarta.validation.Valid;
import kz.bagdat.ticket_booking_api.category.dto.CategoryResponse;
import kz.bagdat.ticket_booking_api.category.dto.CreateCategoryRequest;
import kz.bagdat.ticket_booking_api.category.dto.UpdateCategoryRequest;
import kz.bagdat.ticket_booking_api.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        return categoryService.createCategory(request);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoryRequest request
    ) {
        return categoryService.updateCategory(id, request);
    }
}