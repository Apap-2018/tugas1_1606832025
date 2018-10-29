package com.apap.tugas_1.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas_1.model.InstansiModel;
import com.apap.tugas_1.model.JabatanModel;
import com.apap.tugas_1.model.PegawaiModel;

@Repository
public interface PegawaiDB extends JpaRepository<PegawaiModel, Long>{

	PegawaiModel findByNip(String NIP);
	List <PegawaiModel> findByInstansi(InstansiModel instansi);
	List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	List<PegawaiModel> findByJabatan(JabatanModel jabatan);
	List<PegawaiModel> findByTanggalLahirAndTahunMasukAndInstansi(Date date, String tahunMasuk, InstansiModel instansi);
	
}
