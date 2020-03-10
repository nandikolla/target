package com.target.myretail.service;

import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.target.myretail.web.MyRetailController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
/**
 * @author Ravi Nandikolla
 */
@Service
public class RedskyTargetClient {
    
    @Value("${redskytarget.url}")
    String baseUrl; 

    @Value("${redskytarget.excludes}")
    String excludes;

    private Logger log = LoggerFactory.getLogger(RedskyTargetClient.class);
  
    @Async
    public CompletableFuture<String> getProductNameById(String productId) throws JsonMappingException, JsonProcessingException {
        log.debug("Fetching product details from external RedskyTarget Api by using productId " + productId);
        RestTemplate restTemplate = new RestTemplate();
        String url =  baseUrl + productId + "?" + excludes;
        log.debug("Redsky URI " + url);
        String response = restTemplate.getForObject(url, String.class);
        ObjectNode node = new ObjectMapper().readValue(response, ObjectNode.class);
        String name = null;
        if (node.has("product")
                && node.get("product").has("item")
                && node.get("product").get("item").has("product_description")
                && node.get("product").get("item").get("product_description").has("title")) {
            name =  node.get("product").get("item").get("product_description").get("title").toString();
            log.debug("Product title from RedSkyTarget" + name);
        }
  
        return CompletableFuture.completedFuture(name);
    }
    
}