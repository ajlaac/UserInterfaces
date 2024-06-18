package fri.uv.domaca3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

public class HelloController {

    @FXML
    private DatePicker zacetekDate;

    @FXML
    private DatePicker konecDate;

    @FXML
    private ComboBox drzavaCB;

    @FXML
    private TextField naslov;

    @FXML
    private ComboBox krajCB;

    @FXML
    private ChoiceBox nacin;

    @FXML
    private ChoiceBox tipCB;

    @FXML
    private Spinner odrasliS;

    @FXML
    private Spinner otrociS;

    @FXML
    private DatePicker rojstniDP;

    @FXML
    private ComboBox drzavaN;

    @FXML
    private TextField stKarticeTF;
    @FXML
    private TextField lastnikKarticeTF;

    @FXML
    private TextField ime;

    @FXML
    private TextField priimek;
    @FXML
    private TextField ccv;
    @FXML
    private ComboBox krajN;
    @FXML
    private CheckBox b1;
    @FXML
    private CheckBox b2;
    @FXML
    private CheckBox b3;
    @FXML
    private CheckBox b4;
    @FXML
    private CheckBox b5;
    @FXML
    private CheckBox b6;
    @FXML
    private CheckBox b7;
    @FXML
    private CheckBox b8;

    @FXML
    private void prikaziPomoc() {
        // Ustvarite novo sporočilo tipa AlertType.INFORMATION
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pomoč");
        alert.setHeaderText(null);

        // Ustvarite sliko in jo dodajte v sporočilo
        Image image = new Image(getClass().getResourceAsStream("/fri/uv/domaca3/slika.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300); // Nastavite želeno širino slike
        imageView.setFitHeight(200); // Nastavite želeno višino slike
        alert.setGraphic(imageView);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(imageView);
        stackPane.setAlignment(Pos.CENTER);

        alert.getDialogPane().setContent(stackPane);

        // Prikaz sporočila
        alert.showAndWait();
    }

    private static final int MAX_LENGTH = 10;

    @FXML
    private void preveriVnos(ActionEvent event) {
        String vnos = stKarticeTF.getText();
        // Preverimo, ali je vnos številka in ali je dolžine 16
        if (vnos.matches("\\d{16}")) {
            System.out.println("Veljavna številka kartice: " + vnos);
        } else {
            // Prikažemo Alert, če je vnos neveljaven
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Napaka");
            alert.setHeaderText(null);
            alert.setContentText("Vnesite veljavno številko kartice (16 števk)!");
            alert.showAndWait();
        }
    }

    // Metoda za kapitalizacijo besed
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private boolean isValidName(String str) {
        return str.matches("^[a-zA-Z]+( [a-zA-Z]+)*$");
    }

    private TextFormatter<String> createTextFormatter() {
        return new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("^[a-zA-Z ]*$")) {
                return change;
            } else {
                return null;
            }
        });
    }

    private TextFormatter<String> createCCVFormatter() {
        // Definirajte text formatter, ki sprejema samo 3 številke
        return new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,3}")) { // Preverite, ali je novo besedilo sestavljeno iz največ 3 številk
                return change; // Če je besedilo veljavno, ga sprejmite
            } else {
                return null; // Če besedilo ni veljavno, ga zavržite
            }
        });
    }




    public void initialize() {
        try {
            rojstniDP.setValue(LocalDate.of(2000, Month.JANUARY, 1));
            // Odpremo izvirno datoteko seznam.csv
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\ajlac\\OneDrive\\Desktop\\UV\\domaca3\\src\\main\\resources\\fri\\uv\\domaca3\\seznam.csv"), "UTF-8"));
            String vrstica;

            // Branje vsake vrstice v datoteki
            while ((vrstica = br.readLine()) != null) {
                // Razdelimo vrstico glede na vejico
                String[] deli = vrstica.split(",");
                if (deli.length == 3) {
                    // Združimo preostale dele v en niz
                    String kraj_stevilka = deli[1] + " " + deli[2];
                    // Dodamo vrednost v ComboBox krajCB
                    krajN.getItems().add(kraj_stevilka);
                } else {
                    String kraj_stevilka = deli[0] + " " + deli[1];
                    // Dodamo vrednost v ComboBox krajCB
                    krajN.getItems().add(kraj_stevilka);
                }
            }
            // Zapremo BufferedReader
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ime.setText("");
        priimek.setText("");
        lastnikKarticeTF.setText("");
        ccv.setTextFormatter(createCCVFormatter());
        // Nastavimo text formatter za TextField lastnikKarticeTF
        lastnikKarticeTF.setTextFormatter(createTextFormatter());

        // Privzeto vnesemo ime in priimek v TextField lastnikKartice
        // Privzeto vnesemo kapitalizirano ime in priimek v TextField lastnikKartice
        lastnikKarticeTF.setText(capitalize(ime.getText()) + " " + capitalize(priimek.getText()));

        // Nastavimo poslušalca na TextField-ih za ime in priimek, ki bosta posodobila TextField lastnikKartice
        ime.textProperty().addListener((observable, oldValue, newValue) -> {
            // Preverimo, ali je nova vrednost ustrezna
            if (isValidName(newValue)) {
                // Kapitaliziramo novo vrednost in jo nastavimo v lastnikKarticeTF
                ime.setText(capitalize(newValue));
                lastnikKarticeTF.setText(capitalize(newValue) + " " + capitalize(priimek.getText()));
            } else {
                // Če je nova vrednost neustrezna, ne spremenimo vrednosti
                ime.setText("");
            }
        });

        priimek.textProperty().addListener((observable, oldValue, newValue) -> {
            // Preverimo, ali je nova vrednost ustrezna
            if (isValidName(newValue)) {
                // Kapitaliziramo novo vrednost in jo nastavimo v lastnikKarticeTF
                priimek.setText(capitalize(newValue));
                lastnikKarticeTF.setText(capitalize(ime.getText()) + " " + capitalize(newValue));
            } else {
                // Če je nova vrednost neustrezna, nastavimo prazen niz
                priimek.setText("");
            }
        });


        stKarticeTF.setOnAction(this::preveriVnos);
        // Nastavite celice dneva s pomočjo DayCellFactory
        zacetekDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                // Onemogočite izbiro preteklih datumov
                setDisable(empty || date.isBefore(today));
                // Omeji izbiro na eno leto vnaprej
                if (date.isAfter(today.plusYears(1))) {
                    setDisable(true);
                }
            }
        });

        zacetekDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Preveri, ali je nova vrednost nastavljena
            if (newValue != null) {
                LocalDate minDate = newValue.plusDays(1); // Najmanjši možni datum je naslednji dan po zacetekDate
                LocalDate maxDate = newValue.plusMonths(1); // Največji možni datum je en mesec po zacetekDate

                // Nastavite omejitve za konecDate
                konecDate.setDayCellFactory(picker -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        // Onemogoči izbiro datumov pred minDate in po maxDate
                        setDisable(empty || date.isBefore(minDate) || date.isAfter(maxDate));
                    }
                });
            }
        });


        ObservableList<String> drzave = FXCollections.observableArrayList(
                "Bosna in Hercegovina", "Hrvaška", "Severna Makedonija", "Slovenija",
                "Srbija"
        );
        drzavaCB.setItems(drzave);

        drzavaCB.setOnAction(e -> {
            String izbranaDrzava = (String) drzavaCB.getValue();
            if (izbranaDrzava != null) {
                // Pridobite seznam krajev za izbrano državo iz PlacesDatabase
                List<String> kraji = PlacesDB.getPlacesForCountry(izbranaDrzava);
                // Nastavite seznam krajev kot vsebino ComboBox krajev
                krajCB.setItems(FXCollections.observableArrayList(kraji));
            }
        });

        ObservableList<String> naciniPotovanja = FXCollections.observableArrayList(
                "Avion", "Avtobus", "Vlak", "Lastni prevoz"
        );
        nacin.setItems(naciniPotovanja);

        ObservableList<String> tipiNastanitve = FXCollections.observableArrayList(
                "Soba", "Apartman", "Hiša", "Hotelska nastanitev", "Brez nastanitev"
        );
        tipCB.setItems(tipiNastanitve);

        // Dodajte poslušalca dogodkov za ComboBox tipCB
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        tipCB.setOnAction(e -> {
            String izbranTipNastanitve = (String) tipCB.getValue();
            if (izbranTipNastanitve != null && izbranTipNastanitve.equals("Soba")) {
                ((SpinnerValueFactory.IntegerSpinnerValueFactory) valueFactory).setMax(2);
                ((SpinnerValueFactory.IntegerSpinnerValueFactory) valueFactory2).setMax(2);
            } else if (izbranTipNastanitve != null && izbranTipNastanitve.equals("Apartman")){
                ((SpinnerValueFactory.IntegerSpinnerValueFactory) valueFactory).setMax(4);
                ((SpinnerValueFactory.IntegerSpinnerValueFactory) valueFactory2).setMax(4);
            } else if (izbranTipNastanitve != null && izbranTipNastanitve.equals("Hotelska nastanitev")){
                ((SpinnerValueFactory.IntegerSpinnerValueFactory) valueFactory).setMax(2);
                ((SpinnerValueFactory.IntegerSpinnerValueFactory) valueFactory2).setMax(2);
            } else if (izbranTipNastanitve != null && izbranTipNastanitve.equals("Brez nastanitev")){
                ((SpinnerValueFactory.IntegerSpinnerValueFactory) valueFactory).setMax(10);
                ((SpinnerValueFactory.IntegerSpinnerValueFactory) valueFactory2).setMax(10);
            } else if (izbranTipNastanitve != null && izbranTipNastanitve.equals("Hiša")){
                ((SpinnerValueFactory.IntegerSpinnerValueFactory) valueFactory).setMax(6);
                ((SpinnerValueFactory.IntegerSpinnerValueFactory) valueFactory2).setMax(6);
            }
            ((SpinnerValueFactory.IntegerSpinnerValueFactory) valueFactory).setMin(0);
            ((SpinnerValueFactory.IntegerSpinnerValueFactory) valueFactory2).setMin(0);
        });
        otrociS.setValueFactory(valueFactory2);
        odrasliS.setValueFactory(valueFactory);


        rojstniDP.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Izračunajte razliko v letih med izbranim datumom rojstva in trenutnim datumom
                int leta = LocalDate.now().getYear() - newValue.getYear();
                // Preverite, ali je oseba vsaj 18 let stara
                if (leta < 18) {
                    // Prikažite opozorilo kot Popup
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Opozorilo");
                    alert.setHeaderText(null);
                    alert.setContentText("Oseba mora biti vsaj 18 let stara.");
                    alert.showAndWait();

                    // Nastavite vrednost DatePicker-ja na null
                    rojstniDP.setValue(null);
                }
            }
        });

        ObservableList<String> drzava = FXCollections.observableArrayList(
                "Slovenija"
        );
        drzavaN.setItems(drzava);

    }
    @FXML
    private TextArea textArea;

    @FXML
    private void clearFields(ActionEvent event) {
        textArea.clear();
        krajN.setValue(null);
        b1.setSelected(false);
        b2.setSelected(false);
        b3.setSelected(false);
        b4.setSelected(false);
        b5.setSelected(false);
        b6.setSelected(false);
        b7.setSelected(false);
        b8.setSelected(false);
        zacetekDate.setValue(null);
        konecDate.setValue(null);
        drzavaCB.setValue(null);
        krajCB.setValue(null);
        nacin.setValue(null);
        tipCB.setValue(null);
        odrasliS.getValueFactory().setValue(1);
        otrociS.getValueFactory().setValue(0);
        rojstniDP.setValue(null);
        drzavaN.setValue(null);
        stKarticeTF.clear();
        lastnikKarticeTF.clear();
        ime.setText("");
        priimek.setText("");
        ccv.setText("");
        naslov.setText("");
    }

    @FXML
    private void shranitPodatke(ActionEvent event) {
        // Preverite, ali so vsa polja izpolnjena
        if (zacetekDate.getValue() == null ||
                konecDate.getValue() == null ||
                drzavaCB.getValue() == null ||
                krajCB.getValue() == null ||
                nacin.getValue() == null ||
                tipCB.getValue() == null ||
                rojstniDP.getValue() == null ||
                drzavaN.getValue() == null ||
                stKarticeTF.getText().isEmpty() ||
                lastnikKarticeTF.getText().isEmpty() ||
                ime.getText().isEmpty() ||
                priimek.getText().isEmpty() ||
                ccv.getText().isEmpty()) {
            // Če katero od polj ni izpolnjeno, prikažite opozorilo in ne nadaljujte s shranjevanjem
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Manjkajoči podatki");
            alert.setHeaderText(null);
            alert.setContentText("Prosim, izpolnite vsa polja pred shranjevanjem rezervacije.");
            alert.showAndWait();
            return; // Prekini izvajanje metode, saj ni vseh potrebnih podatkov
        }
        // Dobite izbrane vrednosti iz vmesnikov
        LocalDate zacetniDatum = zacetekDate.getValue();
        LocalDate koncniDatum = konecDate.getValue();
        String izbranaDrzava = (String) drzavaCB.getValue();
        String izbranKraj = (String) krajCB.getValue();
        String izbranNacin = (String) nacin.getValue();
        String izbranTip = (String) tipCB.getValue();
        int stOdraslih = (int) odrasliS.getValue();
        int stOtrok = (int) otrociS.getValue();
        LocalDate rojstniDatum = rojstniDP.getValue();
        String izbranaDrzavaN = (String) drzavaN.getValue();
        String stevilkaKartice = stKarticeTF.getText();
        String lastnikKartice = lastnikKarticeTF.getText();
        String imeStranke = ime.getText();
        String priimekStranke = priimek.getText();
        String ccvKoda = ccv.getText();

        // Izberite datoteko, v katero želite shraniti podatke
        File file = chooseFile();

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))) {
                // Zapišite vse vnose v datoteko
                writer.println("IZBOR DESTINACIJE");
                writer.println("Začetni datum: " + zacetniDatum);
                writer.println("Končni datum: " + koncniDatum);
                writer.println("Izbrana država: " + izbranaDrzava);
                writer.println("Izbran kraj: " + izbranKraj);
                writer.println("Izbran način potovanja: " + izbranNacin);
                writer.println("NASTANITEV");
                writer.println("Izbran tip nastanitve: " + izbranTip);
                writer.println("Število odraslih: " + stOdraslih);
                writer.println("Število otrok: " + stOtrok);
                writer.println("PODATKI O NAROČNIKU");
                writer.println("Ime stranke: " + imeStranke);
                writer.println("Priimek stranke: " + priimekStranke);
                writer.println("Država (naslov): " + izbranaDrzavaN);
                writer.println("Rojstni datum: " + rojstniDatum);
                writer.println("PODATKI KARTICE");
                writer.println("Številka kreditne kartice: " + stevilkaKartice);
                writer.println("Lastnik kreditne kartice: " + lastnikKartice);
                writer.println("CCV koda: " + ccvKoda);

                // Obvestite uporabnika o uspešnem shranjevanju
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Shranjeno");
                alert.setHeaderText(null);
                alert.setContentText("Rezervacija uspešno narejena: " + file.getAbsolutePath());
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                // Če pride do napake pri shranjevanju, obvestite uporabnika o napaki
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Napaka pri shranjevanju");
                alert.setHeaderText(null);
                alert.setContentText("Prišlo je do napake pri shranjevanju podatkov.\n Preverite, če so vsa polja izpolnjena.");
                alert.showAndWait();
            }
        }
    }

    private File chooseFile() {
        // Prikazati morate okno za izbiro direktorija in datoteke
        // Uporabite FileChooser za to
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Izberite mesto za shranjevanje datoteke");

        // Nastavite filtre, da omejite izbiro samo na datoteke s končnico ".txt"
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Prikažite okno za izbiro datoteke in počakajte na uporabnikovo izbiro
        return fileChooser.showSaveDialog(null);
    }




    // Ostala koda vašega kontrolerja...

}
