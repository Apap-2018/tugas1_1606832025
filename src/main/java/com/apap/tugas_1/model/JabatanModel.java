package com.apap.tugas_1.model;


import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;



@Entity
@Table(name= "jabatan")

public class JabatanModel implements Serializable{
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	

	@NotNull
	@Size(max=255)
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size(max=255)
	@Column(name = "deskripsi", nullable = false)
	private String deskripsi;
	
	@NotNull
	@Column(name = "gaji_pokok", nullable = false)
	private double gaji_pokok;
	
	@OneToMany(mappedBy = "jabatan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<JabatanPegawaiModel> jabatanPegawaiList;


	
	@ManyToMany()
	@JoinTable(name = "jabatan_pegawai", joinColumns = { @JoinColumn(name = "id_jabatan") }, inverseJoinColumns = { @JoinColumn(name = "id_pegawai") })
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private List<PegawaiModel> pegawai = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public double getGaji_pokok() {
		return gaji_pokok;
	}

	public void setGaji_pokok(double gaji_pokok) {
		this.gaji_pokok = gaji_pokok;
	}

	public List<JabatanPegawaiModel> getJabatanPegawaiList() {
		return jabatanPegawaiList;
	}

	public void setJabatanPegawaiList(List<JabatanPegawaiModel> jabatanPegawaiList) {
		this.jabatanPegawaiList = jabatanPegawaiList;
	}

	
	public List<PegawaiModel> getPegawai() {
		return pegawai;
	}

	public void setPegawai(List<PegawaiModel> pegawai) {
		this.pegawai = pegawai;
	}
	
	

}
