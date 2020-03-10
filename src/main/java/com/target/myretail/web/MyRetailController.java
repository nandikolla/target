package com.target.myretail.web;

import com.target.myretail.model.Product;
import com.target.myretail.service.MyRetailService;
import com.target.myretail.service.RedskyTargetClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Ravi Nandikolla
 */
@RestController
@RequestMapping("/products")
public class MyRetailController {
    @Autowired
    MyRetailService service;

    @Autowired
    RedskyTargetClient redskyTargetClient;

    private Logger log = LoggerFactory.getLogger(MyRetailController.class);

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        log.debug("Fetching product details for given id " + id);
        Product product = service.getProductById(id);
        if (product == null){
            return new ResponseEntity<>(new Product(), HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable String id) {
        log.debug("Trying to update data for productId " + id);
        if (service.getProduct(id) == null){
            log.error("Unable to find data for id " + id);
            return  new ResponseEntity<>(product, new HttpHeaders(), HttpStatus.NO_CONTENT);
        }

        return  new ResponseEntity<>(service.updateProduct(product), new HttpHeaders(), HttpStatus.OK);
    }   

}