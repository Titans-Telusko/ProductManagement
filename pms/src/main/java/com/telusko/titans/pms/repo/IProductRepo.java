package com.telusko.titans.pms.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telusko.titans.pms.model.Product;

@Repository
public interface IProductRepo extends JpaRepository<Product, Integer> {

}
