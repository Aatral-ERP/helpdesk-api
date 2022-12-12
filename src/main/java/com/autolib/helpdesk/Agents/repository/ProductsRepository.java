package com.autolib.helpdesk.Agents.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Agents.entity.Product;

public interface ProductsRepository extends JpaRepository<Product, Integer> {

	Product findByName(String name);

	Product findById(int id);

	@Query(value = "select DISTINCT(p.category) from Product p")
	List<String> findDistinctCategory();

	@Query(value = "select new Product(p.id,p.name,p.amount,p.amcAmount,p.hsn,p.amchsn,p.finishedProduct,p.stock,p.category) from Product p")
	List<Product> findAllMinDetails();

}
