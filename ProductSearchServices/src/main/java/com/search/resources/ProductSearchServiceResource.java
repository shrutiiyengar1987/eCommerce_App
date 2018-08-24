package com.search.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.search.document.Product;
import com.search.repository.SearchRepository;

@RestController
@RequestMapping("/search")
public class ProductSearchServiceResource {
	
@Autowired
SearchRepository searchRepository;

@RequestMapping("/category/{category}")
public List<Product> findProductsByCategory(@PathVariable int category){
	return searchRepository.findByCategory(category);
}
	
	

}
