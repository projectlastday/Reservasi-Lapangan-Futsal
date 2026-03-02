package reservasi.model;

public class CustomerModel {
    private int idCustomer;
    private String namaCustomer;
    private String noHp;

    public CustomerModel() {}

    public CustomerModel(String namaCustomer, String noHp) {
        this.namaCustomer = namaCustomer;
        this.noHp = noHp;
    }

    public int getIdCustomer() { return idCustomer; }
    public void setIdCustomer(int idCustomer) { this.idCustomer = idCustomer; }

    public String getNamaCustomer() { return namaCustomer; }
    public void setNamaCustomer(String namaCustomer) { this.namaCustomer = namaCustomer; }

    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }

    @Override
    public String toString() {
        return namaCustomer;
    }
}
