package com.pink.pojo;

import java.util.Date;

public class Provider {
    private Integer id; //供应商id
    private String proCode; //供应商编码
    private String proName; //供应商名称
    private String proDesc; //供应商描述
    private String proContact; //供应商联系人
    private String proPhone; //供应商电话
    private String proAddress; //供应商地址
    private String proFax; //供应商传真
    private Integer createdBy; //创建者
    private Date creationBy; //创建日期
    private Integer modifyBy; //更新者
    private Date modifyDate; //更新日期

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProDesc() {
        return proDesc;
    }

    public void setProDesc(String proDesc) {
        this.proDesc = proDesc;
    }

    public String getProContact() {
        return proContact;
    }

    public void setProContact(String proContact) {
        this.proContact = proContact;
    }

    public String getProPhone() {
        return proPhone;
    }

    public void setProPhone(String proPhone) {
        this.proPhone = proPhone;
    }

    public String getProAddress() {
        return proAddress;
    }

    public void setProAddress(String proAddress) {
        this.proAddress = proAddress;
    }

    public String getProFax() {
        return proFax;
    }

    public void setProFax(String proFax) {
        this.proFax = proFax;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationBy() {
        return creationBy;
    }

    public void setCreationBy(Date creationBy) {
        this.creationBy = creationBy;
    }

    public Integer getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
