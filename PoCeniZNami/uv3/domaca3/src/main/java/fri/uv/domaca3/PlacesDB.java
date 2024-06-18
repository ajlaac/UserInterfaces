package fri.uv.domaca3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacesDB {
    private static Map<String, List<String>> data = new HashMap<>();

    // Statični blok za inicializacijo podatkov
    static {
        // Podatki za Bosno in Hercegovino
        List<String> krajiBiH = new ArrayList<>();
        krajiBiH.add("Sarajevo");
        krajiBiH.add("Banja Luka");
        krajiBiH.add("Mostar");
        krajiBiH.add("Tuzla");
        data.put("Bosna in Hercegovina", krajiBiH);

        // Podatki za Hrvaško
        List<String> krajiHrvaska = new ArrayList<>();
        krajiHrvaska.add("Zagreb");
        krajiHrvaska.add("Split");
        krajiHrvaska.add("Rijeka");
        krajiHrvaska.add("Hvar");
        data.put("Hrvaška", krajiHrvaska);

        // Podatki za Severno Makedonijo
        List<String> krajiSevernaMakedonija = new ArrayList<>();
        krajiSevernaMakedonija.add("Skopje");
        krajiSevernaMakedonija.add("Bitola");
        krajiSevernaMakedonija.add("Ohrid");
        krajiSevernaMakedonija.add("Tetovo");
        data.put("Severna Makedonija", krajiSevernaMakedonija);

        // Podatki za Slovenijo
        List<String> krajiSlovenija = new ArrayList<>();
        krajiSlovenija.add("Ljubljana");
        krajiSlovenija.add("Maribor");
        krajiSlovenija.add("Celje");
        krajiSlovenija.add("Bled");
        data.put("Slovenija", krajiSlovenija);

        // Podatki za Srbijo
        List<String> krajiSrbija = new ArrayList<>();
        krajiSrbija.add("Beograd");
        krajiSrbija.add("Novi Sad");
        krajiSrbija.add("Niš");
        krajiSrbija.add("Zlatibor");
        data.put("Srbija", krajiSrbija);
    }

    // Metoda za pridobivanje krajev za določeno državo
    public static List<String> getPlacesForCountry(String country) {
        return data.get(country);
    }
}

