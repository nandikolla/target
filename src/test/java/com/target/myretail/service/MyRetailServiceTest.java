package com.target.myretail.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.target.myretail.dal.ProductRepository;
import com.target.myretail.model.Price;
import com.target.myretail.model.Product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author Ravi Nandikolla
 */
@RunWith(MockitoJUnitRunner.class)
public class MyRetailServiceTest {

    @InjectMocks
    MyRetailService myRetailService;

    @Mock
    ProductRepository productRepository;

    @Mock
    RedskyTargetClient redskyTargetClient;

    @Before
    public void setup() {

    }

    @Test
    public void testGetProduct() throws Exception {
        Product product = new Product();
        product.setId("13860428");
        Price price = new Price();
        price.setCurrencyCode("USD");
        price.setValue("13.49");
        product.setCurrentPrice(price);

        Optional<Product> optionalProduct = Optional.of(product);
        assertTrue(optionalProduct.isPresent());

        when(productRepository.findById("13860428")).thenReturn(optionalProduct);

        Product result = myRetailService.getProduct("13860428");

        assertNotNull(result);
        assertEquals(result.getCurrentPrice().getValue(), "13.49");
        assertEquals(result.getCurrentPrice().getCurrencyCode(), "USD");
        assertEquals(result.getId(), "13860428");

    }

    @Test
    public void testGetProductById() throws Exception {
        Product product = new Product();
        product.setId("13860428");
        Price price = new Price();
        price.setCurrencyCode("USD");
        price.setValue("13.49");
        product.setCurrentPrice(price);

        Optional<Product> optionalProduct = Optional.of(product);
        assertTrue(optionalProduct.isPresent());
        when(redskyTargetClient.getProductNameById("13860428")).thenReturn(CompletableFuture.completedFuture("test"));
        when(productRepository.findById("13860428")).thenReturn(optionalProduct);

        Product result = myRetailService.getProductById("13860428");

        assertNotNull(result);
        assertEquals(result.getCurrentPrice().getValue(), "13.49");
        assertEquals(result.getCurrentPrice().getCurrencyCode(), "USD");
        assertEquals(result.getId(), "13860428");

    }

    @Test
    public void testUpdateProductById() throws Exception {
        Product product = new Product();
        product.setId("13860428");
        Price price = new Price();
        price.setCurrencyCode("USD");
        price.setValue("13.49");
        product.setCurrentPrice(price);

        when(productRepository.save(product)).thenReturn(product);

        Product result = myRetailService.updateProduct(product);

        assertNotNull(result);
        assertEquals(result.getCurrentPrice().getValue(), "13.49");
        assertEquals(result.getCurrentPrice().getCurrencyCode(), "USD");
        assertEquals(result.getId(), "13860428");

    }

}