package id.ac.polban.pbo.kantin.app;

import id.ac.polban.pbo.kantin.model.Mahasiswa;
import id.ac.polban.pbo.kantin.model.MenuItem;
import id.ac.polban.pbo.kantin.model.Pesanan;

public class Main {
    public static void main(String[] args) {
        // T-01: Dua Mahasiswa dengan state berbeda
        Mahasiswa m1 = new Mahasiswa("241001", "Asep");
        Mahasiswa m2 = new Mahasiswa("241002", "Siti");

        // MenuItem awal
        MenuItem nasi = new MenuItem("M01", "Nasi Goreng", 18000);
        MenuItem kopi = new MenuItem("M02", "Kopi Susu", 12000);

        // T-03: Tandai salah satu menu habis
        kopi.tandaiHabis();

        // T-04, T-05, T-06, T-07: Tiga Pesanan
        Pesanan p1 = new Pesanan(m1, nasi, 2); // Valid
        Pesanan p2 = new Pesanan(m2, kopi, 1); // Ditolak (menu habis)
        Pesanan p3 = new Pesanan(m1, nasi, 0); // Ditolak (jumlah 0)

        // Tampilkan ringkasan pesanan
        p1.ringkasan();
        p2.ringkasan();
        p3.ringkasan();

        // T-04: Pengujian Static Method
        System.out.println("Total pesanan yang dibuat: " + Pesanan.getJumlahPesananDibuat());

        // Pengujian Kasir (uses-a relationship)
        Kasir kasir = new Kasir();
        kasir.proses(p1);
        kasir.proses(p2);
    }
}