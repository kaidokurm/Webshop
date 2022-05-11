package ee.kaido.webshop.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ee.kaido.webshop.model.database.Category;
import ee.kaido.webshop.model.database.Subcategory;
import ee.kaido.webshop.repository.SubCategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
public class SubCategoryCache {
    // google guava cache
    @Autowired
    SubCategoryRepository subCategoryRepository;

    //cache loading otsustab, kas v]tab cachest([likiire ja v'ikese j]udlusega
    //v]i v]tab andmebaasist
    private final LoadingCache<Long, Subcategory> subCategoryLoadingCache= CacheBuilder
            .newBuilder()
            .expireAfterAccess(10, TimeUnit.SECONDS)//unustab 'ra 10 sekukundi p'rast
            .build(new CacheLoader<Long, Subcategory>() {
                @Override
                public Subcategory load(Long id) {
                    //siia siis mida teeb kui cachest ei leia
                    log.info("Get Category from DataBase");
                    return subCategoryRepository.findById(id).get();
                }
            });

    //avalik funktsioon mis v]tab cachest kui ei ole siis andmebaasist
    //vaatab k]igepealt
    public Subcategory getSubCategory(Long id) throws ExecutionException {
        log.info("Get Category");
        return subCategoryLoadingCache.get(id);
    }

    //t[hjendab
    public void emptyCache(){
        subCategoryLoadingCache.invalidateAll();
    }

    public void updateCache(Subcategory subCategory){
        subCategoryLoadingCache.put(subCategory.getId(), subCategory);

    }

}
