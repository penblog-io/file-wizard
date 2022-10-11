package io.penblog.filewizard.movers;

import io.penblog.filewizard.services.AttributeService;
import io.penblog.filewizard.interfaces.MoverInterface;
import io.penblog.filewizard.services.ServiceContainer;


abstract class MoverAbstract implements MoverInterface {

    protected final AttributeService attributeService;

    public MoverAbstract() {
        attributeService = ServiceContainer.getAttributeService();
    }

    protected AttributeService getAttributeService() {
        return attributeService;
    }

}
