module it.polimi.ingsw {
    exports it.polimi.ingsw;

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
    requires annotations;
    requires JColor;

    exports it.polimi.ingsw.net to java.rmi;
    opens it.polimi.ingsw.net;

    exports it.polimi.ingsw.controller.rmiInterfaces to java.rmi;
    opens it.polimi.ingsw.controller.rmiInterfaces;

    exports it.polimi.ingsw.controller to java.rmi;
    opens it.polimi.ingsw.controller;

    exports it.polimi.ingsw.model;

    exports it.polimi.ingsw.utils;
    opens it.polimi.ingsw.utils;

    exports it.polimi.ingsw.controller.exceptions;
    opens it.polimi.ingsw.controller.exceptions;

    exports it.polimi.ingsw.view.tui;
    exports it.polimi.ingsw.view;
    exports it.polimi.ingsw.view.gui.chatpage;

    exports it.polimi.ingsw.controller.listeners;
    opens it.polimi.ingsw.controller.listeners;

    exports it.polimi.ingsw.view.gui to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.view.gui to javafx.graphics, javafx.fxml;

    exports it.polimi.ingsw.view.gui.loginpage to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.view.gui.loginpage to javafx.fxml, javafx.graphics;

    exports it.polimi.ingsw.view.gui.board to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.view.gui.board to javafx.fxml, javafx.graphics;

    exports it.polimi.ingsw.view.gui.connectioninterface to  javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.view.gui.connectioninterface to javafx.fxml, javafx.graphics;

    exports it.polimi.ingsw.view.gui.maininterface to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.view.gui.maininterface to javafx.fxml, javafx.graphics;

    exports it.polimi.ingsw.view.gui.customcomponents to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.view.gui.customcomponents to javafx.fxml, javafx.graphics;

    exports it.polimi.ingsw.view.gui.customcomponents.tileview to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.view.gui.customcomponents.tileview to javafx.fxml, javafx.graphics;

    exports it.polimi.ingsw.view.gui.bookshelf to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.view.gui.bookshelf to javafx.fxml, javafx.graphics;

    exports it.polimi.ingsw.view.gui.gameinterface to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.view.gui.gameinterface to javafx.fxml, javafx.graphics;
}