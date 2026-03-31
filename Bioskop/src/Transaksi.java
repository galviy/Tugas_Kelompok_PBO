public class Transaksi implements Pembayaran {
    // atribut
    private String idTransaksi;
    private Customer pembeli;
    private Tiket[] tiketDibeli;
    private int jumlahTiket;
    private int max_tiket;
    
    // konstruktor tanpa parameter
    public Transaksi(){}

    // konstruktor dengan parameter
    public Transaksi(String idTransaksi, Customer pembeli, int maksimalTiket){
        this.idTransaksi = idTransaksi;
        this.pembeli = pembeli;
        this.tiketDibeli = new Tiket[maksimalTiket];
        this.max_tiket = maksimalTiket;
        this.jumlahTiket = 0;
    }

    // getter
    public String getIdTransaksi(){
        return idTransaksi;
    }
    
    public Customer getPembeli(){
        return pembeli;
    }

    public Tiket[] getTiketDibeli(){
        return tiketDibeli;
    }

    public int getJumlahTiket(){
        return jumlahTiket;
    }

    // setter
    public void setIdTransaksi(String idTransaksi){
        this.idTransaksi = idTransaksi;
    }

    public void setPembeli(Customer pembeli){
        this.pembeli = pembeli;
    }

    public void setTiketDibeli(Tiket[] tiketDibeli){
        this.tiketDibeli = tiketDibeli;
    }

    public void setJumlahTiket(int jumlahTiket){
        this.jumlahTiket = jumlahTiket;
    }

    // method dengan try-catch (versi lama DIHAPUS)
    //optimalisasi dengan binary search untuk searching lebih cepat kedepannya. 
    public void tambahTiket(Tiket tiketBaru) {
        try {
            if (tiketBaru == null) {
                throw new IllegalArgumentException("Tiket tidak boleh null!");
            }
            if (tiketBaru.getKursi().getIsBooked() == false) {
                throw new IllegalStateException("Kursi " + tiketBaru.getKursi().getNoKursi() + " belum di-booking!");
            }
            if (jumlahTiket >= tiketDibeli.length) {
                throw new ArrayIndexOutOfBoundsException("Kapasitas transaksi penuh! Maksimal: " + tiketDibeli.length);
            }
            int low = 0;
            int high = jumlahTiket-1;
            int posisiInput = jumlahTiket;
            
             while(low <= high){
                int mid = low + (high - low) / 2;
                String idMid = tiketDibeli[mid].getIdTiket();
                String idBaru = tiketBaru.getIdTiket();

                int hasilBanding = idMid.compareTo(idBaru);
                if (hasilBanding == 0) {
                    throw new IllegalArgumentException(idBaru + " sudah exist!");
                }
                if (hasilBanding < 0) {
                    low = mid + 1;
                }
                else {
                    high = mid - 1;
                    posisiInput = mid;
                }
            }

            for (int i = jumlahTiket; i > posisiInput; i--) {
                tiketDibeli[i] = tiketDibeli[i - 1];
            }
            
            tiketDibeli[posisiInput] = tiketBaru;
            jumlahTiket++;
            System.out.println("[OK] Tiket " + tiketBaru.getIdTiket() + " berhasil ditambahkan.");

        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] Input tidak valid: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("[ERROR] State kursi bermasalah: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // implementasi method interface
    @Override
    public double hitungTotalBayar(){
        double total = 0;
        for (int i = 0; i < jumlahTiket; i++){
            total += tiketDibeli[i].getJadwal().getHarga();
        } 
        return total;
    }

    // method cetak struk
    public void printInfo(){
        System.out.println("-- STRUK TRANSAKSI BIOSKOP --\n");
        System.out.println("ID_Transaksi: " + idTransaksi);
        System.out.println("Nama Pembeli: " + pembeli.getNama());
        System.out.println("-- DAFTAR TIKET YANG DIPESAN --\n");
        for(int i = 0; i < jumlahTiket; i++){
            tiketDibeli[i].printInfo();
            System.out.println("--------------------------");
        }
        System.out.println("Total Bayar: Rp" + hitungTotalBayar());
        System.out.println("--------------------------");
    }
}
