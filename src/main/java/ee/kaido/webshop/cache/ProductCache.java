package ee.kaido.webshop.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ee.kaido.webshop.model.database.Product;
import ee.kaido.webshop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
@Log4j2
@Component
public class ProductCache {
    // google guava cache
    @Autowired
    ProductRepository productRepository;

    //cache loading otsustab, kas v]tab cachest([likiire ja v'ikese j]udlusega
    //v]i v]tab andmebaasist
    private final LoadingCache<Long, Product> productLoadingCache= CacheBuilder
            .newBuilder()
            .expireAfterAccess(10, TimeUnit.SECONDS)//unustab 'ra 10 sekukundi p'rast
            .build(new CacheLoader<Long, Product>() {
                @Override
                public Product load(Long id) {
                    //siia siis mida teeb kui cachest ei leia
                    log.info("Get product from DataBase");
                    return productRepository.findById(id).get();
                }
            });

    //avalik funktsioon mis v]tab cachest kui ei ole siis andmebaasist
    //vaatab k]igepealt
    public Product getProduct(Long id) throws ExecutionException {
        log.info("Get Product");
        return productLoadingCache.get(id);
    }

    //t[hjendab
    public void emptyCache(){
        productLoadingCache.invalidateAll();
    }

    public void updateCache(Product product){
        productLoadingCache.put(product.getId(), product);

    }

}
