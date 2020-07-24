package com.ai.st.microservice.supplies.entities;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "supplies", schema = "supplies")
public class SupplyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "created_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id", referencedColumnName = "id", nullable = false)
	private SupplyStateEntity state;

	@Column(name = "municipality_code", nullable = false, length = 10)
	private String municipalityCode;

	@Column(name = "observations", nullable = true, length = 500)
	private String observations;

	@Column(name = "model_version", nullable = true, length = 20)
	private String modelVersion;

	@Column(name = "type_supply_code", nullable = true)
	private Long typeSupplyCode;

	@Column(name = "request_code", nullable = true)
	private Long requestCode;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(schema = "supplies", name = "supplies_x_classes", joinColumns = @JoinColumn(name = "supply_id"), inverseJoinColumns = @JoinColumn(name = "class_id"))
	List<SupplyClassEntity> classes;

	@OneToMany(mappedBy = "supply", cascade = CascadeType.ALL)
	private List<SupplyAttachmentEntity> attachments = new ArrayList<SupplyAttachmentEntity>();

	@OneToMany(mappedBy = "supply", cascade = CascadeType.ALL)
	private List<SupplyOwnerEntity> owners = new ArrayList<SupplyOwnerEntity>();

	public SupplyEntity() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public SupplyStateEntity getState() {
		return state;
	}

	public void setState(SupplyStateEntity state) {
		this.state = state;
	}

	public String getMunicipalityCode() {
		return municipalityCode;
	}

	public void setMunicipalityCode(String municipalityCode) {
		this.municipalityCode = municipalityCode;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Long getTypeSupplyCode() {
		return typeSupplyCode;
	}

	public void setTypeSupplyCode(Long typeSupplyCode) {
		this.typeSupplyCode = typeSupplyCode;
	}

	public List<SupplyClassEntity> getClasses() {
		return classes;
	}

	public void setClasses(List<SupplyClassEntity> classes) {
		this.classes = classes;
	}

	public List<SupplyAttachmentEntity> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<SupplyAttachmentEntity> attachments) {
		this.attachments = attachments;
	}

	public List<SupplyOwnerEntity> getOwners() {
		return owners;
	}

	public void setOwners(List<SupplyOwnerEntity> owners) {
		this.owners = owners;
	}

	public Long getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(Long requestCode) {
		this.requestCode = requestCode;
	}

	public String getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(String modelVersion) {
		this.modelVersion = modelVersion;
	}

}
