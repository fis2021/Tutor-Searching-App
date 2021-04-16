module org.fis2021 {
    requires javafx.controls;
    requires javafx.fxml;
    requires nitrite;
    requires com.fasterxml.jackson.databind;

    opens org.fis2021 to javafx.fxml, com.fasterxml.jackson.databind;
    exports org.fis2021;
    exports org.fis2021.services;
    opens org.fis2021.services to javafx.fxml;
}