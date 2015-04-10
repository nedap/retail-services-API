package com.nedap.retail.messages;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;


public class ClientExceptionTest {

    private static final String ERROR_REASON = "property id does not exists on object of type Stock";
    private static final String ERROR_MESSAGE = "POST request to www.nedap.com/erp/v2/stock returned response 400";
    private static final String ERROR_MESSAGE_WITH_CLASS = "javax.ws.rs.WebApplicationException: "
            + ERROR_MESSAGE;

    @Test
    public void test_create_from_uniform_interface_exception() {
        final Response clientResponse = Mockito.mock(Response.class);
        final WebApplicationException webApplicationException = new WebApplicationException(ERROR_MESSAGE,
                clientResponse);

        when(clientResponse.getStatus()).thenReturn(404);
        when(clientResponse.readEntity(Matchers.<Class<String>> any())).thenReturn(ERROR_REASON);

        final ClientException clientException = new ClientException(webApplicationException);

        assertEquals(404, clientException.getStatusCode());
        assertEquals(ERROR_REASON, clientException.getErrorReason());
        assertEquals(ERROR_MESSAGE_WITH_CLASS + " with payload: " + ERROR_REASON, clientException.getMessage());
    }

    @Test
    public void test_create_from_uniform_interface_exception_with_null_reason() {
        final Response clientResponse = Mockito.mock(Response.class);
        final WebApplicationException webApplicationException = new WebApplicationException(ERROR_MESSAGE,
                clientResponse);

        when(clientResponse.getStatus()).thenReturn(404);
        when(clientResponse.readEntity(Matchers.<Class<String>> any())).thenReturn(null);

        final ClientException clientException = new ClientException(webApplicationException);

        assertEquals(404, clientException.getStatusCode());
        assertEquals(null, clientException.getErrorReason());
        assertEquals(ERROR_MESSAGE_WITH_CLASS, clientException.getMessage());
    }

    @Test
    public void test_create_from_uniform_interface_exception_with_non_map_payload() {
        final Response clientResponse = Mockito.mock(Response.class);
        final WebApplicationException webApplicationException = new WebApplicationException(ERROR_MESSAGE,
                clientResponse);


        when(clientResponse.getStatus()).thenReturn(404);
        when(clientResponse.readEntity(Matchers.<Class<String>> any())).thenThrow(
                new ResponseProcessingException(clientResponse, ""));

        final ClientException clientException = new ClientException(webApplicationException);

        assertEquals(404, clientException.getStatusCode());
        assertEquals(null, clientException.getErrorReason());
        assertEquals(ERROR_MESSAGE_WITH_CLASS, clientException.getMessage());
    }

}
