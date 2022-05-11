package ee.kaido.webshop.repository;


import ee.kaido.webshop.model.database.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepository extends JpaRepository<Subcategory, Long> {//extend lisab k]ik vidinad interface juurde
}
