package com.nedap.retail.messages.epcis.v1_1.elements;

import com.nedap.retail.messages.epcis.v1_1.cbv.ErrorReasonId;
import io.swagger.annotations.ApiModel;
import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

/**
 * ErrorDeclaration by EPCIS 1.2 Draft.
 * As it is backwards compatible it's put in the v1_1 for ease of use.
 */
@ApiModel
public class ErrorDeclaration {
    public DateTime declarationTime;
    public String reason;
    public List<String> correctiveEventIDs;

    @Override
    public String toString() {
        return "ErrorDeclaration{" +
                "declarationTime=" + declarationTime +
                ",reason=" + reason +
                ",eventId=" + correctiveEventIDs +
                '}';
    }
}
