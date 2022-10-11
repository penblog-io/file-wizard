package io.penblog.filewizard.exceptions;

public class MoveErrorException extends Exception {

    public MoveErrorException() {
        super("MoveErrorException: Cannot move file/folder to destination folder.");
    }

}
