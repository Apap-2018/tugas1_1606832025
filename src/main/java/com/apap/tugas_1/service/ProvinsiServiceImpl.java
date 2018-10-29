package com.apap.tugas_1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas_1.model.ProvinsiModel;
import com.apap.tugas_1.repository.ProvinsiDB;

@Service
@Transactional
public class ProvinsiServiceImpl  implements ProvinsiService{

	@Autowired
	private ProvinsiDB provinsiDb;
	
	@Override
	public List<ProvinsiModel> getAllProvinsi() {
		
		return provinsiDb.findAll();
	}

	@Override
	public ProvinsiModel findProvinsiById(Long id) {
		return provinsiDb.findProvinsiById(id);
	}

}
