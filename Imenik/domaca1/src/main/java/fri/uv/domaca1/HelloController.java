package fri.uv.domaca1;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Label status;

    @FXML
    private TextField ime;

    @FXML
    private TextField priimek;

    @FXML
    private TextField drzava;

    @FXML
    private CheckMenuItem imeCheckItem, priimekCheckItem, drzavaCheckItem;

    @FXML
    private CheckBox dvojniki;

    @FXML Spinner spinner;

    @FXML TextArea sporocilo;

    public void initialize() {
        ime.setDisable(true);
        priimek.setDisable(true);
        drzava.setDisable(true);
        sporocilo.setWrapText(true);

        imeCheckItem.selectedProperty().addListener((observable, oldValue, newValue) -> toggleTextField(ime, newValue));
        priimekCheckItem.selectedProperty().addListener((observable, oldValue, newValue) -> toggleTextField(priimek, newValue));
        drzavaCheckItem.selectedProperty().addListener((observable, oldValue, newValue) -> toggleTextField(drzava, newValue));

        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0));

        spinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                izpisiElement(newValue);
            }
        });

    }


    public void odpriCB(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Izberi datoteko");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));

        if (source instanceof MenuItem) {
            Node parent = ((MenuItem) source).getParentPopup().getOwnerNode();

            if (parent instanceof Node) {
                File selectedFile = fileChooser.showOpenDialog((Stage) parent.getScene().getWindow());
                if (selectedFile != null) {
                    try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                        String line;
                        seznam.getItems().clear();
                        while ((line = br.readLine()) != null) {
                            if (line.matches("\\d+\\. .*, .*, .*")) {
                                line = line.substring(line.indexOf(" ") + 1);
                                seznam.getItems().add(line);
                            } else {
                                prikaziSporocilo("Izbran je napačen format imenika!");
                                break;
                            }
                        }
                    } catch (IOException e) {
                        prikaziSporocilo("Izbran je napačen format imenika!");
                    }
                }
            }
        } else {
            Node source1 = (Node) actionEvent.getSource();
            File selectedFile = fileChooser.showOpenDialog((Stage) source1.getScene().getWindow());
            if (selectedFile != null) {
                try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                    String line;
                    seznam.getItems().clear();
                    while ((line = br.readLine()) != null) {
                        if (line.matches("\\d+\\. .*, .*, .*")) {
                            line = line.substring(line.indexOf(" ") + 1);
                            seznam.getItems().add(line);
                        } else {
                            prikaziSporocilo("Izbran je napačen format imenika!");
                            break;
                        }
                    }

                } catch (IOException e) {
                    prikaziSporocilo("Izbran je napačen format imenika!");
                }
            }
        }
    }


    public void pobrisiCB(ActionEvent actionEvent) {
        clearMessages();
    }

    private void clearMessages() {
        sporocilo.clear();
        status.setText("Status");
    }

    public void izhodCB() {
        Platform.exit();
    }

    public void izpišivseCB(ActionEvent actionEvent) {
        status.setText("Izpisujem...");
        if (seznam.getItems().isEmpty()) {
            prikaziSporocilo("Ni elementov.");
        }
        int counter = 1;
        StringBuilder vsebina = new StringBuilder();
        for (Object element : seznam.getItems()) {
            vsebina.append(counter + ". ");
            counter++;
            vsebina.append(element.toString()).append("\n");
        }
        imenik.setText(vsebina.toString());
    }

    private void izpisiElement(int index) {
        status.setText("Izpisujem...");
        if (index > 0 && index <= seznam.getItems().size()) {
            String izbranElement = seznam.getItems().get(index-1).toString();
            prikaziSporocilo(izbranElement);
        } else {
            prikaziSporocilo("Ni elementa");
        }
    }

    public void omogočiIme() {
        toggleTextField(ime, imeCheckItem.isSelected());
    }

    public void omogočiPriimek() {
        toggleTextField(priimek, priimekCheckItem.isSelected());
    }

    public void omogočiDržavo() {
        toggleTextField(drzava, drzavaCheckItem.isSelected());
    }

    private void toggleTextField(TextField textField, boolean enable) {
        textField.setDisable(!enable);
    }

    private boolean vseOmogoceno() {
        return !ime.isDisabled() && !priimek.isDisabled() && !drzava.isDisabled();
    }

    private boolean vseIzpolnjeno() {
        return !ime.getText().isEmpty() && !priimek.getText().isEmpty() && !drzava.getText().isEmpty();
    }

    private void clearPolja() {
        ime.clear();
        priimek.clear();
        drzava.clear();
    }

    @FXML
    private RadioButton dodajRB, prviRB, izbraniRB;
    
    @FXML ComboBox seznam;

    @FXML TextArea imenik;


    @FXML
    private void izvediAkcijo() {
        if (dodajRB.isSelected()) {
            dodajElement();
        } else if (prviRB.isSelected()) {
            odstraniPrvega();
        } else if (izbraniRB.isSelected()) {
            odstraniIzbranega();
        }
    }

    private void dodajElement() {
        status.setText("Dodajam...");
        if (vseIzpolnjeno() && vseOmogoceno()) {
            System.out.println(ime.getText());
            String novElement = ime.getText() + ", " + priimek.getText() + ", " + drzava.getText();

            if (!seznam.getItems().contains(novElement) || dvojniki.isSelected()) {
                seznam.getItems().add(novElement);
                clearPolja();
            } else {
                prikaziSporocilo("Element že obstaja!");
            }
        } else {
            prikaziSporocilo("Vsa polja morajo biti omogočena in ne smejo biti prazna.");
        }
    }

    private void odstraniPrvega() {
        status.setText("Odstranujem prvi element...");
        if (!seznam.getItems().isEmpty()) {
            seznam.getItems().remove(0);
        } else {
            prikaziSporocilo("Seznam je prazen, nič za odstraniti.");
        }
    }

    private void odstraniIzbranega() {
        status.setText("Odstranujem izbrani element...");
        Object izbraniElement = seznam.getSelectionModel().getSelectedItem();
        if (izbraniElement != null) {
            seznam.getItems().remove(izbraniElement);
        } else {
            prikaziSporocilo("Noben element ni izbran za odstranitev.");
        }
    }


    private void prikaziSporocilo(String s) {
        sporocilo.setText(s);
    }

    public void izvediCB() {
        izvediAkcijo();
    }

    public void shraniCB(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Izberi lokacijo");
        File f = fc.showSaveDialog(null);
        if (f!=null) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                int counter = 1;
                for (Object element : seznam.getItems()) {
                    bw.write(counter + ". ");
                    counter++;
                    bw.write(element.toString());
                    bw.write("\n");
                }
                bw.close();
            } catch (Exception e) {}
        }

    }
}