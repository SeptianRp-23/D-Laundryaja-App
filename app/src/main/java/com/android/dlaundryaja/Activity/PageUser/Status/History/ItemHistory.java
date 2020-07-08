package com.android.dlaundryaja.Activity.PageUser.Status.History;

public class ItemHistory {

    private String invoice, id_user, jenis, tanggal, status;

    public ItemHistory() {
    }

    public ItemHistory(String invoice, String id_user, String jenis, String tanggal, String status) {
        this.invoice = invoice;
        this.id_user = id_user;
        this.jenis = jenis;
        this.tanggal = tanggal;
        this.status = status;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
