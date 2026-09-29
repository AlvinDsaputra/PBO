package id.ac.polban.pbo.kantin.app;

import id.ac.polban.pbo.kantin.model.Pesanan;

public class Kasir {
    public void proses(Pesanan pesanan) {
        if (pesanan.dapatDiproses()) {
            System.out.println("Kasir: Pesanan #" + pesanan.getNomor() + " berhasil diproses.");
        } else {
            System.out.println("Kasir: Pesanan #" + pesanan.getNomor() + " ditolak.");
        }
    }
}