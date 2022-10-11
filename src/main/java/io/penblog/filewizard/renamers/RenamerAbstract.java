package io.penblog.filewizard.renamers;

import io.penblog.filewizard.services.AttributeService;
import io.penblog.filewizard.interfaces.RenamerInterface;
import io.penblog.filewizard.services.ServiceContainer;

abstract class RenamerAbstract implements RenamerInterface {

    private final AttributeService attributeService;

    public RenamerAbstract() {
        attributeService = ServiceContainer.getAttributeService();
    }

    protected AttributeService getAttributeService() {
        return attributeService;
    }
}
