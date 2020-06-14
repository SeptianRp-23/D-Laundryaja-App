package com.android.dlaundryaja.Utils.Adapter.DataItem;

public class DataPesanan {

    private String invoice, id_user, jenis, tanggal, nama, telp, alamat, lokasi, detail, harga, keterangan, status;

    public DataPesanan() {
    }

    public DataPesanan(String invoice, String id_user, String jenis, String tanggal, String nama, String telp, String alamat, String lokasi, String detail, String harga, String keterangan, String status) {
        this.invoice = invoice;
        this.id_user = id_user;
        this.jenis = jenis;
        this.tanggal = tanggal;
        this.nama = nama;
        this.telp = telp;
        this.alamat = alamat;
        this.lokasi = lokasi;
        this.detail = detail;
        this.harga = harga;
        this.keterangan = keterangan;
        this.status = status;
    }

    public String getInvoice() {
        return invoice;
    }

    public String getId_user() {
        return id_user;
    }

    public String getJenis() {
        return jenis;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getNama() {
        return nama;
    }

    public String getTelp() {
        return telp;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getDetail() {
        return detail;
    }

    public String getHarga() {
        return harga;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getStatus() {
        return status;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
