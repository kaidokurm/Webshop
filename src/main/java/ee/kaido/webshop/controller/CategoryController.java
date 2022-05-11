package ee.kaido.webshop.controller;

import ee.kaido.webshop.cache.CategoryCache;
import ee.kaido.webshop.model.database.Category;
import ee.kaido.webshop.repository.CategoryRepository;
import ee.kaido.webshop.repository.SubCategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SubCategoryRepository subCategoryRepository;
    @Autowired
    CategoryCache categoryCache;

    @Operation(description = "Get all Categorys")
    @GetMapping
    public ResponseEntity<List<Category>> getCategory() {
        return ResponseEntity.ok()
                .body(categoryRepository.findAll());
    }

    @Operation(description = "Add a new category")
    @PostMapping
    public ResponseEntity<List<Category>> addCategory(@RequestBody Category category) {
        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED)//created()
                .body(categoryRepository.findAll());
    }

    @Operation(description = "Delete a category with id")
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Category>> deleteCategory(@PathVariable Long id) {
        categoryCache.emptyCache();
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().body(categoryRepository.findAll());
    }

    @Operation(description = "Get a Category by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        try {
            return ResponseEntity.ok()
                    .body(categoryCache.getCategory(id));
        } catch (Exception e) {
            log.error("No category found");
        }
        return null;
    }

    @Operation(description = "Edit a Category")
    @PutMapping
    public List<Category> editCategory(@RequestBody Category category) {
        categoryCache.emptyCache();
        categoryRepository.save(category);
        return categoryRepository.findAll();
    }

}
