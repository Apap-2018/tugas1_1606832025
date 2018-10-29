package com.apap.tugas_1.service;

import java.util.List;

import com.apap.tugas_1.model.ProvinsiModel;

public interface ProvinsiService {

	List<ProvinsiModel> getAllProvinsi();

	ProvinsiModel findProvinsiById(Long long1);
}
