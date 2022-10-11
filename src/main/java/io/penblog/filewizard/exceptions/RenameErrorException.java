package io.penblog.filewizard.exceptions;

public class RenameErrorException extends Exception {

    public RenameErrorException() {
        super("RenameErrorException: Cannot rename file/folder.");
    }
}
