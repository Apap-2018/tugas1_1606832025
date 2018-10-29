package com.apap.tugas_1.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas_1.model.InstansiModel;
import com.apap.tugas_1.model.ProvinsiModel;
import com.apap.tugas_1.service.InstansiService;
import com.apap.tugas_1.service.ProvinsiService;

@Controller
public class InstansiController {
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	
	/*
	 * Method ini untuk menampilkan instansi yang berada pada provinsi yang dipilih
	 * 
	 */
	@RequestMapping(value="/instansi", method = RequestMethod.GET)
	private @ResponseBody Map<String, Object> tampilkanInstansi(@RequestParam(value="idProvinsi") long idProvinsi, Model model) {
		
		//provinsi terpilih
		ProvinsiModel provinsi = provinsiService.findProvinsiById(idProvinsi);
		
		//list instansi = instansi yang dimiliki provinsi untuk jadi option
		List<InstansiModel> allInstansi = instansiService.findInstansiByProvinsi(provinsi);	
		model.addAttribute("allInstansi",allInstansi);
		
		
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("allInstansi", allInstansi);
		
		return output;
	}
}