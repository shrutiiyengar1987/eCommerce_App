package com.search.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.search.document.Product;

@Repository
public interface SearchRepository extends MongoRepository<Product, String> {

	public List<Product> findByCategory(int category);
	
	public List<Product>  findByBrandName(String brandName);
}
