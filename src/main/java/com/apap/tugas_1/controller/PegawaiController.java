package com.apap.tugas_1.controller;

import java.math.BigDecimal;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apap.tugas_1.model.InstansiModel;
import com.apap.tugas_1.model.JabatanModel;
import com.apap.tugas_1.model.JabatanPegawaiModel;
import com.apap.tugas_1.model.PegawaiModel;
import com.apap.tugas_1.model.ProvinsiModel;
import com.apap.tugas_1.service.InstansiService;
import com.apap.tugas_1.service.JabatanService;
import com.apap.tugas_1.service.PegawaiService;
import com.apap.tugas_1.service.ProvinsiService;


@Controller
public class PegawaiController {

	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private JabatanService jabatanService;
	@Autowired
	private ProvinsiService provinsiService;
	
	
	/*
	 * Home
	 */
	@RequestMapping("/")
	private String home(Model model) {
		
		List<JabatanModel> jabatans = jabatanService.getAllJabatan();
		model.addAttribute("jabatanList", jabatans);
		
		List<InstansiModel> instansiList = instansiService.getAllInstansi();
		model.addAttribute("instansiList", instansiList);
		
		return "home";
	}

	
	/*
	 * View Pegawai untuk melihat pegawai sesuai NIP
	 */
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	private String viewPegawai(@RequestParam("nip") String nip, Model model) {
		
		// insiasi model 
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		InstansiModel instansi = pegawai.getInstansi();	
		ProvinsiModel provinsi = instansi.getProvinsi();
		
		// jabatan-jabatan yang ada di pegawai
		List <JabatanPegawaiModel> jabatan_temp = pegawai.getJabatanPegawaiList();
		ArrayList <JabatanModel> jabatanList = new ArrayList <JabatanModel>();
		for (JabatanPegawaiModel jabatantp : jabatan_temp) {
			jabatanList.add(jabatantp.getJabatan());
		}
		
		// gaji pegawai
		BigDecimal gaji = hitungGaji(jabatanList, provinsi);
		
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("instansi", instansi);
		model.addAttribute("provinsi", provinsi);
		model.addAttribute("jabatanList", jabatanList);
		model.addAttribute("gaji", gaji.intValueExact()+"");
		
		return "view_pegawai";
	}
	
	/*
	 * hitung gaji untuk membandingkan gaji tertinggi dan menghitungnya
	 */
	public BigDecimal hitungGaji(ArrayList<JabatanModel> alljabatan, ProvinsiModel provinsi) {
		double maxGaji = provinsi.getPresentase_tunjangan();
		
		for (JabatanModel jabatan : alljabatan) {
			if(jabatan.getGaji_pokok()>=maxGaji) {
				maxGaji=jabatan.getGaji_pokok();
			}
			
		}
		// kalkulasi gaji
		BigDecimal gaji = new BigDecimal(maxGaji);
		BigDecimal tunjangan = new BigDecimal(provinsi.getPresentase_tunjangan());
		BigDecimal persen = new BigDecimal(100);
		BigDecimal gaji_hasil = gaji.add(gaji.multiply(tunjangan.divide(persen)));
		
		return gaji_hasil;
	}
	
	/*
	 * method untuk menampilkan pegawai tertua dan termuda
	 */
	@RequestMapping("/pegawai/termuda-tertua")
	public String viewPegawai (@RequestParam Long idInstansi, Model model) {
		
		// list pegawai terurut turun
		List<PegawaiModel> pegawaiList = pegawaiService.getListPegawaiTerurutUmur(instansiService.findInstansiDetailById(idInstansi));
		
		// pegawai tertua yang paling awal
		model.addAttribute("pegawaiTertua", pegawaiList.get(0));
		
		PegawaiModel pegawaiTua = pegawaiList.get(0);
		
		// mencari jabatan-jabatan yang dimiliki
		List <JabatanPegawaiModel> jabatan_temp = pegawaiTua.getJabatanPegawaiList();
		
		ArrayList <JabatanModel> jabatanList = new ArrayList <JabatanModel>();
		
		for (JabatanPegawaiModel jabatantp : jabatan_temp) {
			
			jabatanList.add(jabatantp.getJabatan());
		}
		model.addAttribute("jabatanTua", jabatanList);
		
		// pegawai termuda yang paling akhir
		model.addAttribute("pegawaiTermuda", pegawaiList.get(pegawaiList.size()-1));
		
		PegawaiModel pegawaiMuda = pegawaiList.get(pegawaiList.size()-1);
		
		// mencari jabatan-jabatan yang dimiliki
		List <JabatanPegawaiModel> jabatan_temp2 = pegawaiMuda.getJabatanPegawaiList();
		
		ArrayList <JabatanModel> jabatanList2 = new ArrayList <JabatanModel>();
		
		for (JabatanPegawaiModel jabatantp : jabatan_temp2) {
			
			jabatanList2.add(jabatantp.getJabatan());
		}
		model.addAttribute("jabatanMuda", jabatanList);
		
		return "view_pegawai_tertua&termuda";
	}
	
	/*
	 * Method  untuk membuat form tambah pegawai
	 */
	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.GET)
	private String tambah_pegawai(Model model) {
		
		// inisiasi untuk form
		List<ProvinsiModel> allProvinsi = provinsiService.getAllProvinsi();
		List<JabatanModel> allJabatan = jabatanService.getAllJabatan();
		PegawaiModel pegawai = new PegawaiModel();
		ArrayList<JabatanModel> jabatan = new ArrayList<JabatanModel>();
		jabatan.add(new JabatanModel());
		pegawai.setJabatan(jabatan);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allJabatan", allJabatan);
		model.addAttribute("allProvinsi",allProvinsi);
		
		
		return "tambah_pegawai";
		
	}
	
	/*
	 * method ketika form sudah diisi
	 */
	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.POST)
	private String tambah(@ModelAttribute PegawaiModel pegawai, RedirectAttributes ra, Model model) {
		//System.out.println("id Pegawai: "+String.valueOf(pegawai.getId()));
		
		// membuat nip baru
		String nip = pegawaiService.buatNIP(pegawai);
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		
		model.addAttribute("nip", pegawai.getNip());
		
		return "tambah";
	}
	
	/*
	 * menambah row jabatan
	 */
	@RequestMapping(value="/pegawai/tambah", params={"addRow"}, method = RequestMethod.POST)
	public String addRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		// inisiasi
		pegawai.getJabatan().add(new JabatanModel());
		List<ProvinsiModel> allProvinsi = provinsiService.getAllProvinsi();
		List<JabatanModel> allJabatan = jabatanService.getAllJabatan();
		List<InstansiModel> allInstansi = instansiService.findInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
				
		model.addAttribute("allInstansi", allInstansi);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allJabatan", allJabatan);
		model.addAttribute("allProvinsi",allProvinsi);
		
	    return "tambah_pegawai";
	}
	
	/*
	 * menhapus row jabatan
	 */
	@RequestMapping(value="/pegawai/tambah", params={"deleteRow"}, method = RequestMethod.POST)
	public String deleteRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
		// inisiasi
		List<ProvinsiModel> allProvinsi = provinsiService.getAllProvinsi();
		List<JabatanModel> allJabatan = jabatanService.getAllJabatan();
		List<InstansiModel> allInstansi = instansiService.findInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("allInstansi", allInstansi);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allJabatan", allJabatan);
		model.addAttribute("allProvinsi",allProvinsi);
		
		// row
		Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
		System.out.println(rowId);
		if (pegawai.getJabatan().size() > rowId.intValue()) {
			pegawai.getJabatan().remove(rowId.intValue());
		}
	    model.addAttribute("pegawai", pegawai);
	    return "tambah_pegawai";
	}
	
	
	/*
	 * method untuk form update pegawai
	 */
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.GET)
	private String ubah(@RequestParam(value="nip") String nip, Model model, RedirectAttributes ra) {
		// mengambil pegawai
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		
		//insiasi
		List<ProvinsiModel> allProvinsi = provinsiService.getAllProvinsi();
		List<JabatanModel> allJabatan = jabatanService.getAllJabatan();
		List<InstansiModel> allInstansi = instansiService.findInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
				
		model.addAttribute("allInstansi", allInstansi);
		model.addAttribute("allJabatan", allJabatan);
		model.addAttribute("allProvinsi",allProvinsi);
		
		model.addAttribute("pegawai", pegawai);
		return "ubah_pegawai";
		
	}
	
	/*
	 * method untuk meng-update pegawai
	 */
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.POST)
	private String ubah(@ModelAttribute PegawaiModel pegawai, Model model) {
		//System.out.println(pegawai.getInstansi().getNama());
		
		// buat NIP baru
		String nip = pegawaiService.buatNIP(pegawai);
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		
		model.addAttribute("nip", pegawai.getNip());
		
		return "update";
		
	}
	
	/*
	 * menambah row jabatan
	 */
	@RequestMapping(value="/pegawai/ubah", params={"addRow"}, method = RequestMethod.POST)
	public String addOnUbah(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		// inisias
		pegawai.getJabatan().add(new JabatanModel());
		List<ProvinsiModel> allProvinsi = provinsiService.getAllProvinsi();
		List<JabatanModel> allJabatan = jabatanService.getAllJabatan();
		List<InstansiModel> allInstansi = instansiService.findInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
		
		model.addAttribute("allInstansi", allInstansi);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allJabatan", allJabatan);
		model.addAttribute("allProvinsi",allProvinsi);
		
	    return "tambah_pegawai";
	}
	
	/*
	 *  menghapus row jabatan
	 */
	@RequestMapping(value="/pegawai/ubah", params={"deleteRow"}, method = RequestMethod.POST)
	public String deleteOnUbah(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
		List<ProvinsiModel> allProvinsi = provinsiService.getAllProvinsi();
		List<JabatanModel> allJabatan = jabatanService.getAllJabatan();
		List<InstansiModel> allInstansi = instansiService.findInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
		
		model.addAttribute("allInstansi", allInstansi);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allJabatan", allJabatan);
		model.addAttribute("allProvinsi",allProvinsi);
		
		// paginator
		Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
		System.out.println(rowId);
		pegawai.getJabatan().remove(rowId.intValue());
	    model.addAttribute("pegawai", pegawai);
	    return "tambah_pegawai";
	}
	
	/*
	 * Method untuk melakukan pencarian pegawai
	 */
	@RequestMapping(value="/pegawai/cari", method=RequestMethod.GET)
	private String cari(@RequestParam(value="idProvinsi", required=false) Optional<Long> idProvinsi, @RequestParam(value="idInstansi", required=false) Optional<Long> idInstansi, @RequestParam(value="idJabatan", required= false) Optional<Integer> idJabatan, Model model) {
		// inisiasi
		PegawaiModel pegawai = new PegawaiModel();
		List<ProvinsiModel> allProvinsi = provinsiService.getAllProvinsi();
		List<JabatanModel> allJabatan = jabatanService.getAllJabatan();
		List<PegawaiModel> allPegawai = pegawaiService.getPegawaiList();
		List<PegawaiModel> selectedPegawai = new ArrayList<PegawaiModel>();
		
		// cari by instansi 
		if (idInstansi.isPresent()) {
			InstansiModel instansi = instansiService.findInstansiDetailById(idInstansi.get()); //get instance instansi
			System.out.println(instansi.getNama());
			
			// cari pegawai by jabatan dan instansi
			if (idJabatan.isPresent()) {
				JabatanModel jabatan = jabatanService.findJabatanDetailById(idJabatan.get());
				System.out.println(jabatan.getNama());
				selectedPegawai = pegawaiService.getPegawaiByInstansiAndJabatan(instansi, jabatan);
			}else {
				// cari berdasarkan instansi
				selectedPegawai = pegawaiService.getPegawaiByInstansi(instansi);
			}
		}else if(idProvinsi.isPresent()) {
			
			// get instance provsinsi
			ProvinsiModel provinsi = provinsiService.findProvinsiById(idProvinsi.get()); 
			// get all possible instansi in the province
			List<InstansiModel> selectedInstansi = instansiService.findInstansiByProvinsi(provinsi); 
			
			// kalau cari by jabatan dan provinsi
			if (idJabatan.isPresent()) {
				JabatanModel jabatan = jabatanService.findJabatanDetailById(idJabatan.get()); 
				// cari pegawai yang punya jabatan tersebut di list possible instansi
				for (InstansiModel instansi : selectedInstansi) {
					System.out.println("selected instansi: "+instansi.getNama());
					selectedPegawai.addAll(pegawaiService.getPegawaiByInstansiAndJabatan(instansi, jabatan));
				}
			}else {
				//cari by provinsi aja
				for (InstansiModel instansi : selectedInstansi) {
					System.out.println("selected instansi: "+instansi.getNama());
					selectedPegawai.addAll(pegawaiService.getPegawaiByInstansi(instansi));
					
				}
			}
		}else if (idJabatan.isPresent()) {
			// cari by jabatan aja
			// get instance provinsi
			JabatanModel jabatan = jabatanService.findJabatanDetailById(idJabatan.get());
					
			selectedPegawai = pegawaiService.getPegawaiByJabatan(jabatan);
		}
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allJabatan", allJabatan);
		model.addAttribute("allProvinsi",allProvinsi);
		model.addAttribute("selectedPegawai", selectedPegawai);
		return "cari_pegawai";
	}
	
}
	

