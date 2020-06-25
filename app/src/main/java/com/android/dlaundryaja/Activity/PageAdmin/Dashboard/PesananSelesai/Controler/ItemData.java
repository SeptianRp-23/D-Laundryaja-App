package com.android.dlaundryaja.Activity.PageAdmin.Dashboard.PesananSelesai.Controler;

public class ItemData {

    private String trInvoice, trJenis, trTanggal, trId_user, trNama_user, trLevel, trStatus;

    public ItemData() {
    }

    public ItemData(String trInvoice, String trJenis, String trTanggal, String trId_user, String trNama_user, String trLevel, String trStatus) {
        this.trInvoice = trInvoice;
        this.trJenis = trJenis;
        this.trTanggal = trTanggal;
        this.trId_user = trId_user;
        this.trNama_user = trNama_user;
        this.trLevel = trLevel;
        this.trStatus = trStatus;
    }

    public String getTrInvoice() {
        return trInvoice;
    }

    public void setTrInvoice(String trInvoice) {
        this.trInvoice = trInvoice;
    }

    public String getTrJenis() {
        return trJenis;
    }

    public void setTrJenis(String trJenis) {
        this.trJenis = trJenis;
    }

    public String getTrTanggal() {
        return trTanggal;
    }

    public void setTrTanggal(String trTanggal) {
        this.trTanggal = trTanggal;
    }

    public String getTrId_user() {
        return trId_user;
    }

    public void setTrId_user(String trId_user) {
        this.trId_user = trId_user;
    }

    public String getTrNama_user() {
        return trNama_user;
    }

    public void setTrNama_user(String trNama_user) {
        this.trNama_user = trNama_user;
    }

    public String getTrLevel() {
        return trLevel;
    }

    public void setTrLevel(String trLevel) {
        this.trLevel = trLevel;
    }

    public String getTrStatus() {
        return trStatus;
    }

    public void setTrStatus(String trStatus) {
        this.trStatus = trStatus;
    }
}
