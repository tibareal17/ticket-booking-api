package kz.bagdat.ticket_booking_api.category.service;

import kz.bagdat.ticket_booking_api.category.dto.CategoryResponse;
import kz.bagdat.ticket_booking_api.category.dto.CreateCategoryRequest;
import kz.bagdat.ticket_booking_api.category.dto.UpdateCategoryRequest;
import kz.bagdat.ticket_booking_api.category.entity.CategoryEntity;
import kz.bagdat.ticket_booking_api.category.repository.CategoryRepository;
import kz.bagdat.ticket_booking_api.common.exception.CategoryAlreadyExistsException;
import kz.bagdat.ticket_booking_api.common.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        return toResponse(category);
    }

    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        String name = request.name().trim();
        String slug = request.slug().trim().toLowerCase();

        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new CategoryAlreadyExistsException("Category with this name already exists");
        }

        if (categoryRepository.existsBySlugIgnoreCase(slug)) {
            throw new CategoryAlreadyExistsException("Category with this slug already exists");
        }

        CategoryEntity category = new CategoryEntity();
        category.setName(name);
        category.setSlug(slug);
        category.setCreatedAt(LocalDateTime.now());

        CategoryEntity savedCategory = categoryRepository.save(category);

        return toResponse(savedCategory);
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        String newName = request.name().trim();
        String newSlug = request.slug().trim().toLowerCase();

        categoryRepository.findByNameIgnoreCase(newName)
                .filter(existingCategory -> !existingCategory.getId().equals(id))
                .ifPresent(existingCategory -> {
                    throw new CategoryAlreadyExistsException("Category with this name already exists");
                });

        categoryRepository.findBySlugIgnoreCase(newSlug)
                .filter(existingCategory -> !existingCategory.getId().equals(id))
                .ifPresent(existingCategory -> {
                    throw new CategoryAlreadyExistsException("Category with this slug already exists");
                });

        category.setName(newName);
        category.setSlug(newSlug);

        CategoryEntity savedCategory = categoryRepository.save(category);

        return toResponse(savedCategory);
    }

    private CategoryResponse toResponse(CategoryEntity entity) {
        return new CategoryResponse(
                entity.getId(),
                entity.getName(),
                entity.getSlug()
        );
    }
}