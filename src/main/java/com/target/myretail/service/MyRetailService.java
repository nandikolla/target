package com.target.myretail.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.target.myretail.dal.ProductRepository;
import com.target.myretail.model.Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Ravi Nandikolla
 */

@Service
public class MyRetailService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    RedskyTargetClient redskyTargetClient;

    private Logger log = LoggerFactory.getLogger(MyRetailService.class);

    /**
     * Building product details from redSkyTarget service and NoSQL data repo
     * @param productId
     * @return product
     */
    public Product getProductById(String productId) {
        log.debug("Fetching product details by using productId" +  productId);
        Product product = null;
        try {
            CompletableFuture<String> title = redskyTargetClient.getProductNameById(productId);
            CompletableFuture<Product> productFuture = getProductByIdFromMongoRepository(productId);
            if (productFuture == null){
                return null;
            }
            product = productFuture.get();
            if (title != null) {
                product.setName(title.get());
            }
        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
           log.error("Unable to parse response as a Json Object" + e.getStackTrace());
        } 
        return product;
    }

    /**
     * 
     * @param productId
     * @return
     */
    @Async
    public CompletableFuture<Product> getProductByIdFromMongoRepository(String productId) {
        log.debug("Fetching product details by using productId" +  productId);
        Product product = getProduct(productId);
        return CompletableFuture.completedFuture(product);
    }

    /**
     * 
     * @param product
     * @return
     */
	public Product updateProduct(Product product) {
        log.debug("Saving product details in NoSQL");
        return productRepository.save(product);
    }
    
    /**
     * Retrieves product details from NoSQL by using its id.
     * @param productId
     * @return product
     */
	public Product getProduct(String productId) {
        log.debug("Fetching product details by using productId" +  productId);
        Optional<Product> findById = productRepository.findById(productId);
        return findById.isPresent() ? findById.get(): null;
	}
     
    
}