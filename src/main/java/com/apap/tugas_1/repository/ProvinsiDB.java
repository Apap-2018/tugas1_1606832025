package com.apap.tugas_1.repository;
import com.apap.tugas_1.model.ProvinsiModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinsiDB extends JpaRepository<ProvinsiModel, Long>{

	ProvinsiModel findProvinsiById(long id);
}
