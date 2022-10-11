open module FileWizard {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires metadata.extractor;
    requires java.logging;
    requires org.jfxtras.styles.jmetro;
    requires org.json;

    exports io.penblog.filewizard.interfaces;
    exports io.penblog.filewizard.controllers;
    exports io.penblog.filewizard.components;
    exports io.penblog.filewizard.exceptions;
    exports io.penblog.filewizard.renamers;
    exports io.penblog.filewizard.movers;
    exports io.penblog.filewizard.services;
    exports io.penblog.filewizard.enums.options;
    exports io.penblog.filewizard.enums;
    exports io.penblog.filewizard;
    exports io.penblog.filewizard.controllers.rename;
    exports io.penblog.filewizard.helpers;
    exports io.penblog.filewizard.guis.dialog;
}
