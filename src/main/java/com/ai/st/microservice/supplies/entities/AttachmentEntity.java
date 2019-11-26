package com.ai.st.microservice.supplies.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "supplies_attachments", schema = "supplies")
public class AttachmentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "created_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "url_documentary_repository", nullable = false, length = 1000)
	private String urlDocumentaryRepository;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supply_id", referencedColumnName = "id", nullable = false)
	private SupplyEntity supply;

	public AttachmentEntity() {

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

	public String getUrlDocumentaryRepository() {
		return urlDocumentaryRepository;
	}

	public void setUrlDocumentaryRepository(String urlDocumentaryRepository) {
		this.urlDocumentaryRepository = urlDocumentaryRepository;
	}

	public SupplyEntity getSupply() {
		return supply;
	}

	public void setSupply(SupplyEntity supply) {
		this.supply = supply;
	}

}
