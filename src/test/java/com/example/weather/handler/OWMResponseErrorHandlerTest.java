package com.example.weather.handler;

import com.example.weather.exception.OWMException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by moksha on 04/07/2016.
 */
public class OWMResponseErrorHandlerTest {
    private OWMResponseErrorHandler owmResponseErrorHandler;

    private ClientHttpResponse clientHttpResponse;

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Before
    public void setup() throws Exception {
        owmResponseErrorHandler = new OWMResponseErrorHandler();
    }

    @After
    public void tearDown() {
        owmResponseErrorHandler = null;
    }

    @Test
    public void verifyCreateOwmResponseErrorHandlerObject(){
        assertNotNull(owmResponseErrorHandler);
    }

    @Test
    public void verifyHasError() throws Exception{
        String exceptionMessage = "Bad Request";
        clientHttpResponse = mock(ClientHttpResponse.class);

        when(clientHttpResponse.getStatusText()).thenReturn(exceptionMessage);
        when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);

        assertThat(owmResponseErrorHandler.hasError(clientHttpResponse)).isEqualTo(true);

    }

    @Test
    public void verifyHandleError() throws Exception{
        String exceptionMessage = "Bad Request";
        exception.expect(OWMException.class);
        exception.expectMessage(containsString(exceptionMessage));
        clientHttpResponse = mock(ClientHttpResponse.class);

        when(clientHttpResponse.getStatusText()).thenReturn(exceptionMessage);
        when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);

        owmResponseErrorHandler.handleError(clientHttpResponse);
    }
}