package io.penblog.filewizard.services;


import java.util.logging.Logger;

abstract public class BaseService {

    private final Logger logger;

    public BaseService(Logger logger) {
        this.logger = logger;
    }

    protected Logger getLogger() {
        return logger;
    }
}
