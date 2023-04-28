package com.yanshen.author.config;



import com.yanshen.author.entity.Result;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DeviceExceptionMapper implements ExceptionMapper<Exception> {

	@SuppressWarnings("static-access")
	@Override
	public Response toResponse(Exception paramE) {
		Response.ResponseBuilder ResponseBuilder = null;
        if (paramE instanceof WebApplicationException){
        	WebApplicationException e = (WebApplicationException) paramE;
        	ResponseBuilder = e.getResponse().ok();
        }else {
        	paramE.printStackTrace();
            ResponseBuilder = Response.ok(Result.fail(null,"other Exception",null), MediaType.APPLICATION_JSON);
        }
        return ResponseBuilder.build();
	}

}
