package com.nedap.retail.messages.epcis.v1_1.cbv;

/**
 * Enumerates Business Steps Specifications follow GS1 EPCIS v1.1 standard + Nedap extensions
 *
 * @see http://nvs0272/confluence/pages/viewpage.action?pageId=9733350
 */
public enum Bizstep {

    /**
     * An object changes possession and/or ownership.
     */
    ACCEPTING(1, "urn:epcglobal:cbv:bizstep:accepting"),
    /**
     * An object arrives at a location
     */
    ARRIVING(2, "urn:epcglobal:cbv:bizstep:arriving"),
    /**
     * One or more objects are combined to create a new finished product. This process is reversible (unlike
     * transforming). The composite object is created during this step, unlike installing.
     */
    ASSEMBLING(3, "urn:epcglobal:cbv:bizstep:assembling"),
    /**
     * An object is picked up and collected for future disposal, recycling or re- used.
     */
    COLLECTING(4, "urn:epcglobal:cbv:bizstep:collecting"),
    /**
     * Associating an instance-level identifier (i.e. EPC) with a particular object. A tag may have been encoded and
     * applied in this step, or may have been previously encoded.
     */
    COMMISSIONING(5, "urn:epcglobal:cbv:bizstep:commissioning"),
    /**
     * Similar to shipping but includes a change in possession and/or ownership at the outbound side.
     *
     * @since v2
     */
    CONSINGING(33, "urn:epcglobal:cbv:bizstep:consinging"),
    /**
     * An instance or increased quantity of class-level identifier is produced.
     *
     * @since v2
     */
    CREATING_CLASS_INSTANCE(34, "urn:epcglobal:cbv:bizstep:creating_class_instance"),
    /**
     * Counting objects within a location in order to obtain an accurate inventory for business needs other than
     * accounting.
     *
     * @since v2
     */
    CYCLE_COUNTING(35, "urn:epcglobal:cbv:bizstep:cycle_counting"),
    /**
     * Disassociating an instance-level identifier (i.e. EPC) with an object. Future re-commissioning is allowed only
     * with a new instance-level identifier.
     */
    DECOMISSIONING(6, "urn:epcglobal:cbv:bizstep:decommissioning"),
    /**
     * An object leaves a location on its way to a destination.
     */
    DEPARTING(7, "urn:epcglobal:cbv:bizstep:departing"),
    /**
     * Terminating an object. - Instance-level identifiers should not be part of subsequent events. When read, this are
     * likely errors such as stray-tag reads. - Class-level identifiers may still be used: their quantities will be
     * reduced (referring to instances that were not destroyed).
     */
    DESTROYING(8, "urn:epcglobal:cbv:bizstep:destroying"),
    /**
     * An object is broken down into separate, uniquely identified component parts.
     */
    DISASSEMBLING(9, "urn:epcglobal:cbv:bizstep:disassembling"),
    /**
     * Writing instance-level identifier (i.e. EPC) to barcode or tag. The identifier is not yet associated with an
     * object at that moment.
     */
    ENCODING(10, "urn:epcglobal:cbv:bizstep:encoding"),
    /**
     * A specific activity at the entrance/exit door of a facility where customers are either leaving with purchased
     * products or entering with product to be returned to the facility.
     */
    ENTERING_EXITING(11, "urn:epcglobal:cbv:bizstep:entering_exiting"),
    /**
     * An object is being segregated for further review.
     */
    HOLDING(12, "urn:epcglobal:cbv:bizstep:holding"),
    /**
     * Process of reviewing objects to address potential physical or documentation defects.
     */
    INSPECTING(13, "urn:epcglobal:cbv:bizstep:inspecting"),
    /**
     * An object is put into a composite object (not merely a container). The composite object exists prior to this
     * step, unlike assembling.
     */
    INSTALLING(14, "urn:epcglobal:cbv:bizstep:installing"),
    /**
     * Terminating an RFID tag previously associated with an object. The object and its instance-level identifier may
     * continue to exist and be the subject of subsequent events (via barcode, manual data entry, replacement tag, etc)
     */
    KILLING(15, "urn:epcglobal:cbv:bizstep:killing"),
    /**
     * An object is loaded into shipping conveyance.
     */
    LOADING(16, "urn:epcglobal:cbv:bizstep:loading"),
    /**
     * A business step not identified by any of the values listed in the core business vocabulary
     */
    OTHER(17, "urn:epcglobal:cbv:bizstep:other"),
    /**
     * Putting objects into a larger container usually for shipping. Aggregation of one unit to another typically occurs
     * at this point.
     */
    PACKING(18, "urn:epcglobal:cbv:bizstep:packing"),
    /**
     * Selecting objects to fill an order.
     */
    PICKING(19, "urn:epcglobal:cbv:bizstep:picking"),
    /**
     * Denotes a specific activity within a business process that indicates that an object is being received at a
     * location and is added to the receiver's inventory. Mutually exclusive with arriving and accepting.
     */
    RECEIVING(20, "urn:epcglobal:cbv:bizstep:receiving"),
    /**
     * An object is taken out of a composite object.
     */
    REMOVING(21, "urn:epcglobal:cbv:bizstep:removing"),
    /**
     * An object's packaging configuration is changed.
     */
    REPACKING(22, "urn:epcglobal:cbv:bizstep:repackaging"),
    /**
     * Malfunctioning object is repaired (typically by a post-sales service), without replacing it by a new one.
     */
    REPAIRING(23, "urn:epcglobal:cbv:bizstep:repairing"),
    /**
     * An object is substituted or exchanged for another object.
     */
    REPLACING(24, "urn:epcglobal:cbv:bizstep:replacing"),
    /**
     * A set of instance-level identifiers - not yet commissioned - are provided for use by another party.
     */
    RESERVING(25, "urn:epcglobal:cbv:bizstep:reserving"),
    /**
     * An item being bought in the past is returned to the store by a customer. Nedap-specific addition
     *
     * @since v2
     */
    RETAIL_RETURNING(36, "http://nedapretail.com/bizstep/retail_returning"),
    /**
     * At the point-of-sale. Transferring ownership to a customer in exchange for something of value (currency, credit,
     * etc).
     */
    RETAIL_SELLING(27, "urn:epcglobal:cbv:bizstep:retail_selling"),
    /**
     * A potential customer tries a specific item in a fitting room. Nedap-specific addition
     *
     * @since v2
     */
    RETAIL_TRYING(37, "http://nedapretail.com/bizstep/retail_trying"),
    /**
     * Indicates the overall process of staging_outbound, loading and departing. - Used when more granular process step
     * information is unknown or inaccessible. - The use of shipping is mutually exclusive from the use of
     * staging_outbound, loading and departing.
     */
    SHIPPING(28, "urn:epcglobal:cbv:bizstep:shipping"),
    /**
     * An object moves from facility to an area where it will await transport pick-up.
     */
    STAGING_OUTBOUND(29, "urn:epcglobal:cbv:bizstep:staging_outbound"),
    /**
     * Counting objects within a location following established rules and/or standard to serve as a basis for accounting
     * purposes.
     *
     * @since v2
     */
    STOCK_TAKING(38, "urn:epcglobal:cbv:bizstep:stock_taking"),
    /**
     * Make an object available within a location to the customer or for order fulfillment within a DC.
     */
    STOCKING(30, "urn:epcglobal:cbv:bizstep:stocking"),
    /**
     * An object is moved into and out of storage within a location.
     *
     * @since v2
     */
    STORING(31, "urn:epcglobal:cbv:bizstep:storing"),
    /**
     * One or more objects are an input into a process that irreversibly changes the object(s) into a new object or
     * objects. The output has a new identity and characteristics.
     *
     * @since v2
     */
    TRANSFORMING(32, "urn:epcglobal:cbv:bizstep:transforming"),
    /**
     * Moving an object from one location to another using a vehicle.
     *
     * @since v2
     */
    TRANSPORTING(39, "urn:epcglobal:cbv:bizstep:transporting"),
    /**
     * An object is unloaded from a shipping conveyance.
     *
     * @since v2
     */
    UNLOADING(40, "urn:epcglobal:cbv:bizstep:unloading"),
    /**
     * Removing objects from a larger container, usually after receiving or accepting. Disaggregation of one unit from
     * another typically occurs at this point.
     *
     * @since v2
     */
    UNPACKING(41, "urn:epcglobal:cbv:bizstep:unpacking");

    public final int number;
    public final String bizStep;

    private Bizstep(final int aNumber, final String aBizStep) {
        number = aNumber;
        bizStep = aBizStep;
    }

    @Override
    public String toString() {
        return number + ":" + bizStep;
    }

    /**
     * Provides similar functionality to valueOf(..). However, valueOf does not know how to access values() to lookup
     * the biz step. Instead, it will access name() which does not match its serialized counterpart.
     *
     * Example: Enum value: UNPACKING Serialized value (which is looked for): urn:epcglobal:cbv:bizstep:unpacking
     *
     * @param value String value of requested biz step
     * @return Bizstep enum value for provided String
     * @see valueOf(..)
     */
    public static Bizstep permissiveValueOf(final String value) {
        if (value == null) {
            throw new IllegalArgumentException("biz step missing");
        }
        for (final Bizstep bizStep : values()) {
            if (value.equalsIgnoreCase(bizStep.bizStep)) {
                return bizStep;
            }
        }
        throw new IllegalArgumentException("unknown biz step");
    }
}
