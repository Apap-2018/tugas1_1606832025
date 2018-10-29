package com.apap.tugas_1.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.apap.tugas_1.model.JabatanModel;
import com.apap.tugas_1.service.JabatanService;
import com.apap.tugas_1.service.PegawaiService;


@Controller
public class JabatanController {
	
	@Autowired
	private JabatanService jabatanService;
	@Autowired
	private PegawaiService pegawaiService;
	
	
	
	/*
	 * Method untuk melihat detail jabatan
	 */
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	public String viewJabatan(@RequestParam(value = "idJabatan", required = true) Long idJabatan, Model model) {
		
		// ambil jabatan yang terpilih
		JabatanModel archieve = jabatanService.findJabatanDetailById(idJabatan);
		model.addAttribute("jabatan", archieve);
		
		/*
		 * soal bonus
		 * menghitung jumlah pegawai pada suatu jabatan
		 */
		int jumlah = archieve.getPegawai().size();
		model.addAttribute("jumlah", jumlah);
		
		return "view_jabatan";
	}
	
	/*
	 * Membuat form jabatan
	 */
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String addJabatan(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "tambah_jabatan";
	}
	
	/*
	 * Mensimpan data jabatan
	 */
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan) {
		jabatanService.addJabatan(jabatan);
		
		return "tambah";
	}
	
	/*
	 * Mengubah jabatan form dari view_jabatan
	 */
	@RequestMapping(value="/jabatan/ubah", method = RequestMethod.GET)
	private String updateJabatan(@RequestParam(value="idJabatan", required=true) String idJabatan, Model model) {
		long idd = Long.parseLong(idJabatan);
		JabatanModel jabatanLama = jabatanService.findJabatanDetailById(idd);
		
		model.addAttribute("jabatan", jabatanLama);
		return "update_jabatan";
	}
	
	/*
	 * Menyimpan jabatan yang telah di update
	 */
	@RequestMapping(value="/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.updateJabatan(jabatan.getId(), jabatan);
		return "update";
	}
	
	/*
	 * Menghapus jabatan terpilih dari dari view_jabatan
	 */
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	public String deleteJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		JabatanModel cur_jabatan = jabatanService.findJabatanDetailById(jabatan.getId());
		
		// kalo jabatannya masih ada, akan mengarah ke pesan error
		if(cur_jabatan.getPegawai().size()>0) {
			return "delete_jabatan_error";
		}else {
			jabatanService.deleteJabatanById(jabatan.getId());
			return "delete_jabatan";
		}
		
		
	}
	
	/*
	 * Untuk melihat semua jabatan yang ada
	 */
	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	public String viewjabatan(Model model) {
		
		List<JabatanModel> jabatanList = jabatanService.getAllJabatan();
		
		//paginator
		HashMap<String, Integer> pegawaiNumOnJabatan = pegawaiService.getPegawaiNumOnJabatan(jabatanList);
		
		model.addAttribute("jabatanList", jabatanList);
		
		model.addAttribute("pegawaiNumOnJabatan", pegawaiNumOnJabatan);
		
		return "view_all_jabatan";
	}
	
	
	


}
