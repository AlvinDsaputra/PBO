import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class CountrySetDemo {
    public static void main(String[] args) {
        // Step 10: Negara tanpa duplikasi
        HashSet<String> countries = new HashSet<>();
        countries.add("Indonesia");
        countries.add("Japan");
        countries.add("Malaysia");
        countries.add("Singapore");
        countries.add("Thailand");
        countries.add("Indonesia"); 

        System.out.println("Isi HashSet countries: " + countries);
        System.out.println("Jumlah elemen = " + countries.size());

        System.out.println("\nApakah ada Indonesia? " + countries.contains("Indonesia"));
        countries.remove("Japan");
        System.out.println("Setelah remove Japan: " + countries);
        System.out.println("Size sekarang: " + countries.size());

        HashSet<String> countriesSet = new HashSet<>();
        countriesSet.add("Japan");
        countriesSet.add("Indonesia");
        countriesSet.add("Malaysia");

        ArrayList<String> countriesList = new ArrayList<>();
        countriesList.add("Japan");
        countriesList.add("Indonesia");
        countriesList.add("Malaysia");

        Collections.sort(countriesList);
        System.out.println("\nHasil sort ArrayList: " + countriesList);

        ArrayList<String> sortedCountries = new ArrayList<>(countriesSet);
        Collections.sort(sortedCountries);
        System.out.println("Hasil sort dari Set (via List baru): " + sortedCountries);
    }
}