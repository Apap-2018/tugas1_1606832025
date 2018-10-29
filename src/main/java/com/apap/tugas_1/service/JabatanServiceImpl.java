package com.apap.tugas_1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.apap.tugas_1.model.JabatanModel;
import com.apap.tugas_1.repository.JabatanDB;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{

	
	@Autowired
	private JabatanDB jabatanDb;
	
	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
		
	}

	

	@Override
	public JabatanModel findJabatanDetailById(long id) {
		// TODO Auto-generated method stub
		return jabatanDb.findJabatanById(id);
	}
	
	@Override
	public void updateJabatan(long id, JabatanModel jabatan) {
		JabatanModel old = jabatanDb.findJabatanById(id);
		old.setNama(jabatan.getNama());
		old.setDeskripsi(jabatan.getDeskripsi());
		old.setGaji_pokok(jabatan.getGaji_pokok());
		jabatanDb.save(old);
		
	}

	@Override
	public void deleteJabatanById(long id) {
		jabatanDb.deleteById(id);
		
	}



	@Override
	public List<JabatanModel> getAllJabatan() {
		// TODO Auto-generated method stub
		return jabatanDb.findAll();
	}

	
	
	 
	
    

}
