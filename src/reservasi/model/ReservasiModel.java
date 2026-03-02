package reservasi.model;

import java.sql.Date;

public class ReservasiModel {

    private int idReservasi;
    private int idCustomer;
    private Date tanggal;
    private int idLapangan;
    private int totalJam;
    private double totalHarga;
    private int idStatusReservasi;

    public ReservasiModel() {
    }

    public ReservasiModel(int idCustomer, Date tanggal, int idLapangan, int totalJam, double totalHarga, int idStatusReservasi) {
        this.idCustomer = idCustomer;
        this.tanggal = tanggal;
        this.idLapangan = idLapangan;
        this.totalJam = totalJam;
        this.totalHarga = totalHarga;
        this.idStatusReservasi = idStatusReservasi;
    }

    public int getIdReservasi() {
        return idReservasi;
    }

    public void setIdReservasi(int idReservasi) {
        this.idReservasi = idReservasi;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public int getIdLapangan() {
        return idLapangan;
    }

    public void setIdLapangan(int idLapangan) {
        this.idLapangan = idLapangan;
    }

    public int getTotalJam() {
        return totalJam;
    }

    public void setTotalJam(int totalJam) {
        this.totalJam = totalJam;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public int getIdStatusReservasi() {
        return idStatusReservasi;
    }

    public void setIdStatusReservasi(int idStatusReservasi) {
        this.idStatusReservasi = idStatusReservasi;
    }
}
