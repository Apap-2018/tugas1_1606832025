package com.apap.tugas_1.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas_1.model.InstansiModel;
import com.apap.tugas_1.model.JabatanModel;
import com.apap.tugas_1.model.PegawaiModel;
import com.apap.tugas_1.repository.PegawaiDB;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{

	
	@Autowired
	private PegawaiDB pegawaiDb;
	
	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
	}

	@Override
	public List<PegawaiModel> getPegawaiList() {
		return pegawaiDb.findAll();
	}

	@Override
	public List<PegawaiModel> getListPegawaiTerurutUmur(InstansiModel instansi) {
		return pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan) {
		List<PegawaiModel> hasil = new ArrayList<>();
		
		for(PegawaiModel pegawai : pegawaiDb.findByInstansi(instansi)) {
			if (pegawai.getJabatan().contains(jabatan)) {
				hasil.add(pegawai);
			}
		}
		
		return hasil;
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
		return pegawaiDb.findByInstansi(instansi);
	}

	@Override
	public List<PegawaiModel> getPegawaiByProvinsiAndJabatan(long provinsiId, JabatanModel jabatan) {
		List<PegawaiModel> hasil = new ArrayList<>();
		
		for(PegawaiModel pegawai : this.getPegawaiByProvinsi(provinsiId)) {
			if (pegawai.getJabatan().contains(jabatan)) {
				hasil.add(pegawai);
			}
		}
		
		return hasil;
	}

	@Override
	public List<PegawaiModel> getPegawaiByProvinsi(long provinsiId) {
		List<PegawaiModel> hasil = new ArrayList<>();
		
		for(PegawaiModel pegawai : pegawaiDb.findAll()) {
			if (pegawai.getInstansi().getProvinsi().getId() == provinsiId) {
				hasil.add(pegawai);
			}
		}
		
		return hasil;
	}

	@Override
	public List<PegawaiModel> getPegawaiByJabatan(JabatanModel jabatan){
		return pegawaiDb.findByJabatan(jabatan);
	}

	@Override
	public String buatNIP(PegawaiModel pegawai) {
		
		// klo udah ada nipnya, kalo mau update pegawai
		if(pegawai.getNip() != null) {
			PegawaiModel oldPegawai = pegawaiDb.getOne(pegawai.getId());
			
			// kalo instansi dan tahun masuk dan tgl lahir ga berubah, pake nip yang lama
			if(pegawai.getInstansi().equals(oldPegawai.getInstansi()) && pegawai.getTahunMasuk().equals(oldPegawai.getTahunMasuk())
				&& pegawai.getTanggalLahir().equals(oldPegawai.getTanggalLahir())	
					) {
				return pegawai.getNip();
			}
		}
		
		
		// buat nip
		
		String instansi = String.valueOf(pegawai.getInstansi().getId());
		
		String tahunMasuk = String.format("%04d", Integer.parseInt((pegawai.getTahunMasuk())));
		
		int no = 1;
		
		DateFormat dateF = new SimpleDateFormat("ddMMyy");
		String tglLahir = dateF.format(pegawai.getTanggalLahir());
		List<PegawaiModel> listTglLahirTahunMasukSama = pegawaiDb.findByTanggalLahirAndTahunMasukAndInstansi(pegawai.getTanggalLahir(), pegawai.getTahunMasuk(), pegawai.getInstansi());
		
		// ini kalo tanggal lahir dan tahun masuk dan instansinya sama, maka akan diurutin
		if (listTglLahirTahunMasukSama.size()>=1) {
			System.out.println(listTglLahirTahunMasukSama.get(listTglLahirTahunMasukSama.size()-1).getNama());
			String lastElementSameNip = listTglLahirTahunMasukSama.get(listTglLahirTahunMasukSama.size()-1).getNip();
			String lastNoUrut = lastElementSameNip.substring(14, 16);
			System.out.println("last NIP: "+ lastElementSameNip);
			System.out.println("Last No urut: "+lastNoUrut);
			no = Integer.parseInt(lastNoUrut)+1;
		}
		
		String nip = instansi.concat(tglLahir).concat(tahunMasuk).concat(String.format("%02d", no));
		
		return nip;
		
	}
	
	//pagination
	@Override
	public HashMap<String, Integer> getPegawaiNumOnJabatan(List<JabatanModel> allJabatan){
		HashMap<String, Integer> jabatanWithPegawaiNum = new HashMap<String, Integer>();
		for (JabatanModel jabatan : allJabatan) {
			jabatanWithPegawaiNum.put(String.valueOf(jabatan.getId()), pegawaiDb.findByJabatan(jabatan).size());
			
		}
		return jabatanWithPegawaiNum;
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

