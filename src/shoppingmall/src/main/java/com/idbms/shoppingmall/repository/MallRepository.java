package com.idbms.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idbms.shoppingmall.model.Mall;


@Repository("mallRepository")
public interface MallRepository extends JpaRepository<Mall, Integer> {
	 Mall findByMallId(int mallId);
	 
}
