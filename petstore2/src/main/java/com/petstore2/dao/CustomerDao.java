package com.petstore2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petstore2.entity.Customer;

public interface CustomerDao extends JpaRepository <Customer, Long> {

}
