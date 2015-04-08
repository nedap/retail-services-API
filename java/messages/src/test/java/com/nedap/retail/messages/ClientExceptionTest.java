package com.nedap.retail.messages;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;

public class ClientExceptionTest {

    private static final String ERROR_REASON = "property id does not exists on object of type Stock";
    private static final String ERROR_MESSAGE = "POST request to www.nedap.com/erp/v2/stock returned response 400";
    private static final String ERROR_MESSAGE_WITH_CLASS = "com.sun.jersey.api.client.UniformInterfaceException: "
            + ERROR_MESSAGE;

    @Test
    public void test_create_from_uniform_interface_exception() {
        final ClientResponse clientResponse = Mockito.mock(ClientResponse.class);
        final UniformInterfaceException uniformInterfaceException = new UniformInterfaceException(ERROR_MESSAGE,
                clientResponse);

        when(clientResponse.getStatus()).thenReturn(404);
        when(clientResponse.getEntity(Matchers.<Class<String>> any())).thenReturn(ERROR_REASON);

        final ClientException clientException = new ClientException(uniformInterfaceException);

        assertEquals(404, clientException.getStatusCode());
        assertEquals(ERROR_REASON, clientException.getErrorReason());
        assertEquals(ERROR_MESSAGE_WITH_CLASS + " with payload:" + ERROR_REASON, clientException.getMessage());
    }

    @Test
    public void test_create_from_uniform_interface_exception_with_null_reason() {
        final ClientResponse clientResponse = Mockito.mock(ClientResponse.class);
        final UniformInterfaceException uniformInterfaceException = new UniformInterfaceException(ERROR_MESSAGE,
                clientResponse);

        when(clientResponse.getStatus()).thenReturn(404);
        when(clientResponse.getEntity(Matchers.<Class<String>> any())).thenReturn(null);

        final ClientException clientException = new ClientException(uniformInterfaceException);

        assertEquals(404, clientException.getStatusCode());
        assertEquals(null, clientException.getErrorReason());
        assertEquals(ERROR_MESSAGE_WITH_CLASS, clientException.getMessage());
    }

    @Test
    public void test_create_from_uniform_interface_exception_with_non_map_payload() {
        final ClientResponse clientResponse = Mockito.mock(ClientResponse.class);
        final UniformInterfaceException uniformInterfaceException = new UniformInterfaceException(ERROR_MESSAGE,
                clientResponse);

        when(clientResponse.getStatus()).thenReturn(404);
        when(clientResponse.getEntity(Matchers.<Class<String>> any())).thenThrow(
                new ClientHandlerException());

        final ClientException clientException = new ClientException(uniformInterfaceException);

        assertEquals(404, clientException.getStatusCode());
        assertEquals(null, clientException.getErrorReason());
        assertEquals(ERROR_MESSAGE_WITH_CLASS, clientException.getMessage());
    }

}
