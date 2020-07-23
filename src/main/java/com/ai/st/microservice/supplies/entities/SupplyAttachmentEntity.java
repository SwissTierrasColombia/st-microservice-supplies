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
public class SupplyAttachmentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "created_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "data", nullable = false, length = 2000)
	private String data;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supply_id", referencedColumnName = "id", nullable = false)
	private SupplyEntity supply;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "attachment_type_id", referencedColumnName = "id", nullable = false)
	private SupplyAttachmentTypeEntity attachmentType;

	public SupplyAttachmentEntity() {

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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public SupplyEntity getSupply() {
		return supply;
	}

	public void setSupply(SupplyEntity supply) {
		this.supply = supply;
	}

	public SupplyAttachmentTypeEntity getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(SupplyAttachmentTypeEntity attachmentType) {
		this.attachmentType = attachmentType;
	}

}
