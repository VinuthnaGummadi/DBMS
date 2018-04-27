package com.idbms.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idbms.shoppingmall.model.Bid;
import com.idbms.shoppingmall.model.Floors;

@Repository("bidRepository")
public interface BidRepository extends JpaRepository<Bid, Integer>{

	Bid findByBidID(int bidID);
}
