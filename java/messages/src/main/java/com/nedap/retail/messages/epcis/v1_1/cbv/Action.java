package com.nedap.retail.messages.epcis.v1_1.cbv;

import io.swagger.annotations.ApiModel;

/**
 * Enumerates EPCIS Actions Specifications follow GS1 EPCIS v1.1 standard + Nedap extensions
 */
@ApiModel
public enum Action {

    /**
     * The entity in question has been created or added to.
     */
    ADD(1, "ADD"),
    /**
     * The entity in question has not been changed: it has neither been created, added to, destroyed, or removed from.
     */
    OBSERVE(2, "OBSERVE"),
    /**
     * The entity in question has been removed from or destroyed altogether.
     */
    DELETE(3, "DELETE");

    private final int number;
    private final String action;

    private Action(final int aNumber, final String anAction) {
        number = aNumber;
        action = anAction;
    }

    public final int getNumber() {
        return number;
    }

    public final String action() {
        return action;
    }

    @Override
    public final String toString() {
        return this.name();
    }
}
