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

    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "observations", length = 500)
    private String observations;

    @Column(name = "model_version", length = 20)
    private String modelVersion;

    @Column(name = "type_supply_code")
    private Long typeSupplyCode;

    @Column(name = "request_code")
    private Long requestCode;

    @Column(name = "manager_code", nullable = false)
    private Long managerCode;

    @Column(name = "is_valid")
    private Boolean isValid;

    @OneToMany(mappedBy = "supply", cascade = CascadeType.ALL)
    private List<SupplyAttachmentEntity> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "supply", cascade = CascadeType.ALL)
    private List<SupplyOwnerEntity> owners = new ArrayList<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(Long managerCode) {
        this.managerCode = managerCode;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }
}
