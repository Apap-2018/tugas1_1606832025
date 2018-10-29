package com.apap.tugas_1.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas_1.model.JabatanModel;


@Repository
public interface JabatanDB extends JpaRepository<JabatanModel, Long>, PagingAndSortingRepository<JabatanModel,Long>{
	
	JabatanModel findJabatanById(long id);
	
}
