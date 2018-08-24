package com.product.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.product.document.Product;
import com.product.repository.ProductRepository;

@RestController
@RequestMapping("/products")
public class AddProductServiceResource{
	
	@Autowired
	ProductRepository productRepository;
	
	@RequestMapping(method=RequestMethod.POST, value="/add")
    public String save(@RequestBody Product product) {
        productRepository.save(product);
        return product.getId();
    }

}


