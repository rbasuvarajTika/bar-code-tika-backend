package com.tika.barcode.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tika.barcode.entity.Territory;


public interface TerritoryRepository<T> extends JpaRepository<Territory, Integer>{
	
	Optional<Territory> findByTerritoryId(Integer id);

}
