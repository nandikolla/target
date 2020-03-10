package com.target.myretail.web;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.myretail.model.Price;
import com.target.myretail.model.Product;
import com.target.myretail.service.MyRetailService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
 
/**
 * @author Ravi Nandikolla
 */
@RunWith(MockitoJUnitRunner.class)
public class MyRetailControllerTest
{

    @InjectMocks
    MyRetailController myRetailController;

    @Autowired
    MockMvc mvc;

    @Mock
    MyRetailService service;

   @Before
    public void setup(){
        mvc = MockMvcBuilders.standaloneSetup(myRetailController).build();
    }

    /* private static String asJsonString(final Object obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    } */
 
    @Test
    public void testGetProduct() throws Exception
    {

        Product product = new Product();        
        product.setId("13860428");
        Price price = new Price();
        price.setCurrencyCode("USD");
        price.setValue("13.49");
        product.setCurrentPrice(price);
       
        when(service.getProductById("13860428")).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders
        .get("/products/13860428")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is("13860428")))
        .andExpect(jsonPath("$.current_price.currency_code", is("USD")))
        .andExpect(jsonPath("$.current_price.value", is("13.49")));
    }
        

    @Test
    public void testUpdateProduct() throws Exception
    {
        Product product = new Product();        
        product.setId("13860428");
        Price price = new Price();
        price.setCurrencyCode("USD");
        price.setValue("13.49");
        product.setCurrentPrice(price);
       
        when(service.getProduct("13860428")).thenReturn(null);
        //when(service.updateProduct(product)).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders
            .put("/products/13860428")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(product))
            .accept(MediaType.APPLICATION_JSON))            
            .andExpect(status().isNoContent());

    }

    @Test
    public void testUpdateProductWithData() throws Exception
    {
        Product product = new Product();        
        product.setId("13860428");
        Price price = new Price();
        price.setCurrencyCode("USD");
        price.setValue("13.49");
        product.setCurrentPrice(price);
       
        when(service.getProduct("13860428")).thenReturn(product);
        when(service.updateProduct(product)).thenReturn(product);

        mvc.perform(MockMvcRequestBuilders
            .put("/products/13860428")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(product))
            .accept(MediaType.APPLICATION_JSON))            
            .andExpect(status().isOk());

    }


    private static String asJsonString(final Object obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }
 
}