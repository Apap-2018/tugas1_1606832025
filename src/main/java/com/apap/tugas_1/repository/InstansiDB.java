package com.apap.tugas_1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas_1.model.InstansiModel;
import com.apap.tugas_1.model.ProvinsiModel;




@Repository
public interface InstansiDB extends JpaRepository<InstansiModel, Long>{
	
	InstansiModel findInstansiById(long id);
	List<InstansiModel> findByProvinsi(ProvinsiModel provinsi);
}
