package kz.bagdat.ticket_booking_api.category.repository;

import kz.bagdat.ticket_booking_api.category.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsBySlugIgnoreCase(String slug);

    Optional<CategoryEntity> findByNameIgnoreCase(String name);

    Optional<CategoryEntity> findBySlugIgnoreCase(String slug);
}
