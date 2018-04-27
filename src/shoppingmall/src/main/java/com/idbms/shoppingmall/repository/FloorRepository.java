package com.idbms.shoppingmall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idbms.shoppingmall.model.Floors;


@Repository("floorsRepository")
public interface FloorRepository extends JpaRepository<Floors, Integer> {

	Floors findByFloorID(int floorID);
	
	Floors findByFloorLevel(String floorLevel);
}
