module org.example.todolistfe {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.fasterxml.jackson.databind;

    opens org.example.todolistfe to javafx.fxml;
    exports org.example.todolistfe;
}