package ee.kaido.webshop.controller;

import ee.kaido.webshop.cache.SubCategoryCache;
import ee.kaido.webshop.model.database.Subcategory;
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
@RequestMapping("subcategory")
public class SubCategoryController {

    @Autowired
    SubCategoryRepository subCategoryRepository;
    @Autowired
    SubCategoryCache subCategoryCache;

    @Operation(description = "Get all Categorys")
    @GetMapping
    public ResponseEntity<List<Subcategory>> getSugCategory() {
        return ResponseEntity.ok()
                .body(subCategoryRepository.findAll());
    }

    @Operation(description = "Add a new category")
    @PostMapping
    public ResponseEntity<List<Subcategory>> addSubCategory(@RequestBody Subcategory subcategory) {//responceEntity juures saan vahetada
        subCategoryRepository.save(subcategory);
        return ResponseEntity.status(HttpStatus.CREATED)//created()
                .body(subCategoryRepository.findAll());
    }

    @Operation(description = "Delete a category with id")
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Subcategory>> deleteSubCategory(@PathVariable Long id) {
        subCategoryCache.emptyCache();//teeme vahem'lu t[hjaks kui toode kustutatakse v]i muudetakse  //categoryCache.emptyCache();
        subCategoryRepository.deleteById(id);
        return ResponseEntity.ok().body(subCategoryRepository.findAll());
    }

    @Operation(description = "Get a SubCategory by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Subcategory> getSubCategory(@PathVariable Long id) {
        try {
            return ResponseEntity.ok()
                    .body(subCategoryCache.getSubCategory(id));
        } catch (Exception e) {
            log.error("No subcategory found");
        }
        return null;
    }

    @Operation(description = "Edit a SubCategory")
    @PutMapping
    public List<Subcategory> editCategory(@RequestBody Subcategory subcategory) {
        subCategoryCache.emptyCache();
        subCategoryRepository.save(subcategory);
        return subCategoryRepository.findAll();
    }

}
