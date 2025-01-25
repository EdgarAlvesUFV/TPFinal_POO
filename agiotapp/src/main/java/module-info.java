module edu.ufv.agiotapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens edu.ufv.agiotapp to javafx.fxml;
    exports edu.ufv.agiotapp;
}
