package com.apap.tugas_1.service;

import java.util.List;

import com.apap.tugas_1.model.InstansiModel;
import com.apap.tugas_1.model.ProvinsiModel;

public interface InstansiService {
	
	InstansiModel findInstansiDetailById(long id);
	List<InstansiModel> getAllInstansi();
	List<InstansiModel> findInstansiByProvinsi(ProvinsiModel provinsi);

}
