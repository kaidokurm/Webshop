package ee.kaido.webshop.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ee.kaido.webshop.model.database.Category;
import ee.kaido.webshop.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
public class CategoryCache {
    // google guava cache
    @Autowired
    CategoryRepository categoryRepository;
    private final LoadingCache<Long, Category> categoryLoadingCache= CacheBuilder
            .newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build(new CacheLoader<Long, Category>() {
                @Override
                public Category load(Long id) {
                    log.info("Get Category from DataBase");
                    return categoryRepository.findById(id).get();
                }
            });

    public Category getCategory(Long id) throws ExecutionException {
        log.info("Get Category");
        return categoryLoadingCache.get(id);
    }
    public void emptyCache(){
        categoryLoadingCache.invalidateAll();
    }

    public void updateCache(Category category){
        categoryLoadingCache.put(category.getId(), category);

    }

}
