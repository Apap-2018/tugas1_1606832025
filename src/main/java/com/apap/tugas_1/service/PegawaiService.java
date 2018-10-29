package com.apap.tugas_1.service;

import java.util.HashMap;
import java.util.List;

import com.apap.tugas_1.model.InstansiModel;
import com.apap.tugas_1.model.JabatanModel;
import com.apap.tugas_1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	void addPegawai(PegawaiModel pegawai);
	List <PegawaiModel> getPegawaiByInstansi(InstansiModel instansi);
	List <PegawaiModel> getPegawaiList();
	List <PegawaiModel> getPegawaiByProvinsi(long provinsi);
	
	List<PegawaiModel> getListPegawaiTerurutUmur(InstansiModel instansi);
	List<PegawaiModel> getPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan);
	List<PegawaiModel> getPegawaiByProvinsiAndJabatan(long instansi, JabatanModel jabatan);
	List<PegawaiModel> getPegawaiByJabatan(JabatanModel jabatan);
	String buatNIP(PegawaiModel pegawai);
	HashMap<String, Integer> getPegawaiNumOnJabatan(List<JabatanModel> allJabatan);
}
