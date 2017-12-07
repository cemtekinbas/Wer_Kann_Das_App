package de.hsmannheim.mso.wkd.WerKannDas.Models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int  pk;
    private String mail;
    private String user_name;
    private String plz;
    private String password;
    private String forename;
    private String surename;
    private String village;
    private String street_with_number;
    private Date birthdate;

    public User(int pk, String mail, String user_name, String plz, String password, String forename, String surename, String village, String street_with_number, Date birthdate) {
        this.pk = pk;
        this.mail = mail;
        this.user_name = user_name;
        this.plz = plz;
        this.password = password;
        this.forename = forename;
        this.surename = surename;
        this.village = village;
        this.street_with_number = street_with_number;
        this.birthdate = birthdate;
    }

    public User (ResultSet results) throws SQLException {
        this.pk = results.getInt("pk");
        this.mail = results.getString("mail");
        this.user_name = results.getString("user_name");
        this.plz = results.getString("plz");
        this.password = results.getString("password");
        this.forename = results.getString("forename");
        this.surename = results.getString("surename");
        this.village = results.getString("village");
        this.street_with_number = results.getString("street_with_number");
        this.birthdate = results.getDate("birthdate");
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getStreet_with_number() {
        return street_with_number;
    }

    public void setStreet_with_number(String street_with_number) {
        this.street_with_number = street_with_number;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
