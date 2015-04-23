package com.nedap.retail.messages.epcis.v1_1.cbv;

/**
 * Enumerates EPCIS Dispositions Specifications follow GS1 EPCIS v1.1 standard + Nedap extensions
 *
 * @see http://nvs0272/confluence/pages/viewpage.action?pageId=9733350
 */
public enum Disposition {

    /**
     * A commissioned object has just been introduced into the supply chain.
     */
    ACTIVE(0, "urn:epcglobal:cbv:disp:active"),
    /**
     * Object has been loaded onto a container, the doors have been closed and the shipment sealed.
     */
    CONTAINER_CLOSED(1, "urn:epcglobal:cbv:disp:container_closed"),
    /**
     * Object is impaired in its usefulness and/or reduced in value due to a defect.
     *
     * @since v2
     */
    DAMAGED(19, "urn:epcglobal:cbv:disp:damaged"),
    /**
     * Object has been fully rendered non-usable
     */
    DESTROYED(2, "urn:epcglobal:cbv:disp:destroyed"),
    /**
     * Object has been returned for disposal.
     *
     * @since v2
     */
    DISPOSED(20, "urn:epcglobal:cbv:disp:disposed"),
    /**
     * An instance-level identifier has been written to a barcode or RFID tag, but not yet commissioned.
     */
    ENCODED(3, "urn:epcglobal:cbv:disp:encoded"),
    /**
     * Object is past expiration date.
     *
     * @since v2
     */
    EXPIRED(21, "urn:epcglobal:cbv:disp:expired"),
    /**
     * Decommissioned object that may be reintroduced to the supply chain.
     */
    INACTIVE(4, "urn:epcglobal:cbv:disp:inactive"),
    /**
     * Default disposition for object proceeding through points in the supply chain.
     */
    IN_PROGRESS(5, "urn:epcglobal:cbv:disp:in_progress"),
    /**
     * Object being shipped between two trading partners.
     */
    IN_TRANSIT(6, "urn:epcglobal:cbv:disp:in_transit"),
    /**
     * Object is maybe stolen by a person. It might or might not be in the store anymore. Nedap-specific
     *
     * @since v2
     */
    MAYBE_STOLEN(25, "http://nedapretail.com/disp/maybe_stolen"),
    /**
     * In validating the pedigree for the object, no match was found, causing the product to be quarantined for further
     * investigation and disposition.
     *
     * @since v2
     */
    NO_PEDIGREE_MATCH(22, "urn:epcglobal:cbv:disp:no_pedigree_match"),
    /**
     * Product is non-sellable because current date is past expiration date
     *
     * @see EXPIRED
     * @deprecated
     */
    NON_SELLABLE_EXPIRED(7, "urn:epcglobal:cbv:disp:non_sellable_expired"),
    /**
     * Product that cannot be sold because it has a flaw
     *
     * @see DAMAGED
     * @deprecated
     */
    NON_SELLABLE_DAMAGED(8, "urn:epcglobal:cbv:disp:non_sellable_damaged"),
    /**
     * Product is non-sellable because it has been returned for disposal
     *
     * @see DISPOSED
     * @deprecated
     */
    NON_SELLABLE_DISPOSED(9, "urn:epcglobal:cbv:disp:non_sellable_disposed"),
    /**
     * In validating the pedigree for the product, no match was found, causing the product to be quarantined for further
     * investigation and disposition
     *
     * @see NO_PEDIFREE_MATCH
     * @deprecated
     */
    NON_SELLABLE_NO_PEDIGREE_MATCH(10, "urn:epcglobal:cbv:disp:non_sellable_no_pedigree_match"),
    /**
     * Product cannot be sold to a customer.
     */
    NON_SELLABLE_OTHER(11, "urn:epcglobal:cbv:disp:non_sellable_other"),
    /**
     * Product is non-sellable because of public safety reasons.
     *
     * @see RECALLED
     * @deprecated
     */
    NON_SELLABLE_RECALLED(12, "urn:epcglobal:cbv:disp:non_sellable_recalled"),
    /**
     * Product is non-sellable because of public safety reasons.
     */
    RECALLED(23, "urn:epcglobal:cbv:disp:recalled"),
    /**
     * Instance-level identifier has been allocated for a third party.
     */
    RESERVED(13, "urn:epcglobal:cbv:disp:reserved"),
    /**
     * Product has been purchased by a customer.
     */
    RETAIL_SOLD(17, "urn:epcglobal:cbv:disp:retail_sold"),
    /**
     * Object has been sent back for various reasons. It may or may not be sellable.
     */
    RETURNED(14, "urn:epcglobal:cbv:disp:returned"),
    /**
     * Product can be sold as is and customer can access product for purchase.
     */
    SELLABLE_ACCESSIBLE(15, "urn:epcglobal:cbv:disp:sellable_accessible"),
    /**
     * Product can be sold as is, but customer cannot access product for purchase.
     */
    SELLABLE_NOT_ACCESSIBLE(16, "urn:epcglobal:cbv:disp:sellable_not_accessible"),
    /**
     * An object has been taken without permission or right.
     *
     * @since v2
     */
    STOLEN(24, "urn:epcglobal:cbv:disp:stolen"),
    /**
     * Product condition is not known
     */
    UNKNOWN(18, "urn:epcglobal:cbv:disp:unknown");

    private final int number;
    private final String disposition;

    private Disposition(final int aNumber, final String aDisposition) {
        number = aNumber;
        disposition = aDisposition;
    }

    public final int number() {
        return number;
    }

    public final String disposition() {
        return disposition;
    }

    /**
     * Provides similar functionality to valueOf(..). However, valueOf does not know how to access values() to lookup
     * the Disposition. Instead, it will access name() which does not match its serialized counterpart.
     *
     * Example: Enum value: IN_TRANSIT Serialized value (which is looked for): urn:epcglobal:cbv:disp:in_transit
     *
     * @param value String value of requested disposition
     * @return Disposition enum value for provided String
     * @see valueOf(..)
     */
    public static Disposition permissiveValueOf(final String value) {
        if (value == null) {
            throw new IllegalArgumentException("disposition missing");
        }
        for (final Disposition d : values()) {
            if (value.equalsIgnoreCase(d.disposition)) {
                return d;
            }
        }
        throw new IllegalArgumentException("unknown disposition");
    }
}
