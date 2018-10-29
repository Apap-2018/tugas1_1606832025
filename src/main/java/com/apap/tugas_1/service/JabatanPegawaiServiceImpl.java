package com.apap.tugas_1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas_1.model.JabatanPegawaiModel;
import com.apap.tugas_1.repository.JabatanPegawaiDB;

@Service
@Transactional
public class JabatanPegawaiServiceImpl implements JabatanPegawaiService{

	@Autowired
	private JabatanPegawaiDB jpdbw;
	
	@Override
	public List<JabatanPegawaiModel> getAllJabatanPegawai() {
		
		return jpdbw.findAll();
	}

	

	
	}