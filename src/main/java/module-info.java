module it.polimi.ingsw {

    opens it.polimi.ingsw.view.loginpage;

    requires java.rmi;
    requires com.google.gson;
    opens it.polimi.ingsw.model;

    requires json.simple;
    requires javafx.controls;
    requires javafx.fxml;
        requires javafx.web;

        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires net.synedra.validatorfx;
            requires org.kordamp.ikonli.javafx;
            requires org.kordamp.bootstrapfx.core;
            requires com.almasb.fxgl.all;

    requires java.desktop;
    requires java.compiler;

    opens it.polimi.ingsw.view to javafx.fxml;
    exports it.polimi.ingsw;

    exports it.polimi.ingsw.view.loginpage to javafx.fxml, javafx.graphics;
}