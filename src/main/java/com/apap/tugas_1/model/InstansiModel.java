package com.apap.tugas_1.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name= "instansi")

public class InstansiModel implements Serializable{
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;


	@NotNull
	@Size(max=50)
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size(max=255)
	@Column(name = "deskripsi", nullable = false)
	private String deskripsi;
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_provinsi", referencedColumnName = "id", nullable=false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private ProvinsiModel provinsi;


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


	public ProvinsiModel getProvinsi() {
		return provinsi;
	}


	public void setProvinsi(ProvinsiModel provinsi) {
		this.provinsi = provinsi;
	}
	
	
}
