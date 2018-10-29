package com.apap.tugas_1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.apap.tugas_1.model.JabatanModel;

@Service
public interface JabatanService {

	void addJabatan(JabatanModel jabatan);
	List <JabatanModel> getAllJabatan();
	JabatanModel findJabatanDetailById(long id);
	void updateJabatan(long id, JabatanModel jabatan);
	void deleteJabatanById(long id);
	
}
