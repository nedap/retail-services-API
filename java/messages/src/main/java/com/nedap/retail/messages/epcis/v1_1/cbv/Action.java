package com.nedap.retail.messages.epcis.v1_1.cbv;

/**
 * Enumerates EPCIS Actions Specifications follow GS1 EPCIS v1.1 standard + Nedap extensions
 *
 * @see http://nvs0272/confluence/pages/viewpage.action?pageId=9733350
 */
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

    public final int number;
    public final String action;

    private Action(final int aNumber, final String anAction) {
        number = aNumber;
        action = anAction;
    }

    @Override
    public final String toString() {
        return this.name();
    }
}
