module uv.fri.drugadomaca {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.jsoup;

    opens uv.fri.drugadomaca to javafx.fxml;
    exports uv.fri.drugadomaca;
}
