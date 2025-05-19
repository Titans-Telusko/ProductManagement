package com.telusko.titans.pms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telusko.titans.pms.model.ProductBrand;
@Repository
public interface IProductBrandRepo extends JpaRepository<ProductBrand, Integer> {

	ProductBrand findByBrandNameIgnoreCase(String productBrand);

}
