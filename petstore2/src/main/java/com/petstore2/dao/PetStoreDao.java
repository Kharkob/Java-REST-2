package com.petstore2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petstore2.entity.PetStore;

public interface PetStoreDao extends JpaRepository< PetStore, Long> {

}
