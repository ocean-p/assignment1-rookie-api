package com.daiduong.demo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class AccountEntity {
    
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "role_id")
    private int roleId;

    @Column(name = "is_delete")
    private boolean isDeleted;
    
    public AccountEntity() {
    }

    public AccountEntity(String username, String password, String fullName, String phone, String address,
            LocalDate createDate, LocalDate updateDate, int roleId, boolean isDeleted) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.roleId = roleId;
        this.isDeleted = isDeleted;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
