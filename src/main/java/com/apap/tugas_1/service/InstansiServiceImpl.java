package com.apap.tugas_1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas_1.model.InstansiModel;
import com.apap.tugas_1.model.ProvinsiModel;
import com.apap.tugas_1.repository.InstansiDB;


@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{

	@Autowired
	private InstansiDB instansiDb;
	
	
	@Override
	public InstansiModel findInstansiDetailById(long id) {
		return instansiDb.findInstansiById(id);
	}

	@Override
	public List<InstansiModel> getAllInstansi() {
		return instansiDb.findAll();
	}

	@Override
	public List<InstansiModel> findInstansiByProvinsi(ProvinsiModel provinsi) {
		return instansiDb.findByProvinsi(provinsi);
	}

	
	
	
	
	

}
