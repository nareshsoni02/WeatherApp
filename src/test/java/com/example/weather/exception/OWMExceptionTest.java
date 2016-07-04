package com.example.weather.exception;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * Created by moksha on 04/07/2016.
 */
public class OWMExceptionTest {
    private  OWMException owmException;
    private HttpStatus statusCode = HttpStatus.ACCEPTED;
    private String msg = "Accepted";
    @Before
    public void setup() throws Exception {
        owmException = new OWMException(statusCode,msg);
    }

    @After
    public void tearDown() {
        owmException = null;
    }

    @Test
    public void verifyCreateDefaultOWMExceptionlObject(){
        String message = "message";
        owmException = new OWMException(message);
        assertNotNull(owmException);
    }

    @Test
    public void verifyCreateOWMExceptionlObject(){
        assertNotNull(owmException);
    }

    @Test
    public void verifyStatusCode(){
        assertThat(owmException.getStatusCode()).isEqualTo(statusCode);
    }

    @Test
    public void verifyMessage(){
        assertThat(owmException.getMessage()).isEqualTo(msg);
    }

}