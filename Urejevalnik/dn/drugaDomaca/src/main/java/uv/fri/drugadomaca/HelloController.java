package uv.fri.drugadomaca;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

import static java.lang.System.*;

public class HelloController {

    public TextArea zapisi;
    public TextField najdiTF;
    public TextField zamenjajTF;
    public Accordion harmonika;
    public TitledPane htmlP;
    public TextField najdizTF;
    public TitledPane txtP;
    public TitledPane zapisiP;
    public TextArea txt;
    @FXML
    private Label welcomeText;

    @FXML
    private HTMLEditor editor;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"); // Ustvarim SimpleDateFormat


    public void initialize() {
        zamenjajTF.setManaged(false);
        zamenjajTF.setVisible(false);
        najdiTF.setManaged(false);
        najdiTF.setVisible(false);
        najdizTF.setVisible(false);
        najdizTF.setManaged(false);

        txt.setWrapText(true);


        harmonika.setExpandedPane(htmlP);

        najdiTF.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    harmonika.setExpandedPane(txtP);
                    txtP.requestFocus();
                    String tekst = txt.getText();
                    String iskano = najdiTF.getText();
                    int indeks = tekst.indexOf(iskano);
                    if (indeks != -1) {
                        txt.requestFocus();
                        txt.positionCaret(indeks);
                    }
                }
            }
        });

        zamenjajTF.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    if (!najdizTF.getText().equals("") && !zamenjajTF.getText().equals("")) {
                        harmonika.setExpandedPane(txtP);
                        txtP.requestFocus();
                        String najdi = najdizTF.getText();
                        String zamenjaj = zamenjajTF.getText();
                        String tekst = txt.getText();
                        tekst = tekst.replaceAll(najdi, zamenjaj);
                        txt.setText(tekst);
                        logActivity("Zamenjava uspešno končana");
                    } else {
                        zapisi.requestFocus();
                        logActivity("Izpolnite polja najdi in zamenjaj!");
                        harmonika.setExpandedPane(zapisiP);
                    }
                }
            }
        });

        najdizTF.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    if (!najdizTF.getText().equals("") && !zamenjajTF.getText().equals("")) {
                        harmonika.setExpandedPane(txtP);
                        String najdi = najdizTF.getText();
                        String zamenjaj = zamenjajTF.getText();
                        String tekst = txt.getText();
                        tekst = tekst.replaceAll(najdi, zamenjaj);
                        txt.setText(tekst);
                        logActivity("Zamenjava uspešno končana");
                    } else {
                        zapisi.requestFocus();
                        logActivity("Izpolnite polja najdi in zamenjaj!");
                        harmonika.setExpandedPane(zapisiP);
                    }
                }
            }
        });

        harmonika.expandedPaneProperty().addListener((obs, oldPane, newPane) -> {
            if (newPane != null) {
                String ime = newPane.getId();
                if (ime.equals("htmlP")) {
                    editor.setHtmlText(txt.getText());
                } else if (ime.equals("txtP")) {
                    String formattedHtml = formatHtml(editor.getHtmlText());
                    txt.setText(formattedHtml);
                }
            }
        });

    }

    public String formatHtml(String rawHtml) {
        // Parsanje surove HTML kode z uporabo knjižnice jsoup
        Document doc = Jsoup.parseBodyFragment(rawHtml);

        // Nastavitev zamika in tabulatorjev
        doc.outputSettings().indentAmount(4).prettyPrint(true);

        // Pretvorba oblikovane HTML kode nazaj v niz
        return doc.toString();
    }



    public void odpriCB() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Izberi datoteko");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Html files (*.html)", "*.html"));
        File f = fc.showOpenDialog(null);
        if (f != null) {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line;
                while ( (line = br.readLine()) != null) sb.append(line).append("\n");
                editor.setHtmlText(sb.toString());
                logActivity("Datoteka uspešno naložena.");
            } catch (Exception e) {
                logActivity("Neuspešno nalaganje datoteke!");
            }
        } 
    }

    public void shraniCB() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Izberi lokacijo");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Html files (*.html)", "*.html"));
        File f = fc.showSaveDialog(null);
        if (f != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                bw.write(editor.getHtmlText());
                logActivity("Datoteka je uspešno shranjena.");
            } catch (IOException e) {
                logActivity("Shranjevanje ni uspelo!");
            } ;
        }
    }

    public void izhodCB(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void najdiCB() {
        zamenjajTF.setManaged(false);
        zamenjajTF.setVisible(false);
        najdizTF.setVisible(false);
        najdizTF.setManaged(false);
        if (najdiTF.isVisible()) {
            najdiTF.setManaged(false);
            najdiTF.setVisible(false);
            najdiTF.setText("");
        } else {
            najdiTF.setManaged(true);
            najdiTF.setVisible(true);
        }
    }

    public void zamenjajCB() {
        najdiTF.setManaged(false);
        najdiTF.setVisible(false);
        if (zamenjajTF.isVisible()) {
            zamenjajTF.setManaged(false);
            zamenjajTF.setVisible(false);
            najdizTF.setManaged(false);
            najdizTF.setVisible(false);
            najdizTF.setText("");
            zamenjajTF.setText("");
        } else {
            zamenjajTF.setManaged(true);
            zamenjajTF.setVisible(true);
            najdizTF.setManaged(true);
            najdizTF.setVisible(true);
        }
    }

    public void aboutCB() {
        zapisi.requestFocus();
        harmonika.setExpandedPane(zapisiP);
        logActivity("Avtor editorja je študentka Ajla Čaušević.");
    }

    private void logActivity(String activity) {
        Date now = new Date();
        String timestamp = dateFormatter.format(now);
        String text = timestamp + " - " + activity + "\n";
        zapisi.appendText(text);
        zapisi.selectRange(zapisi.getLength() - text.length(), zapisi.getLength());

    }
}