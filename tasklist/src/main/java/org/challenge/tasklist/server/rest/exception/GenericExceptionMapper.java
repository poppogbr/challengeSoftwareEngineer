package org.challenge.tasklist.server.rest.exception;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.challenge.tasklist.model.dto.ErrorMessageDTO;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Inject
	private Logger logger;
	
    @Override
    public Response toResponse(final Throwable ex) {
    	
    	logger.error(ex.getMessage(), ex);
    	
    	ErrorMessageDTO errorDTO = new ErrorMessageDTO();
    	errorDTO.setMessage(StringEscapeUtils.escapeJson(ExceptionUtils.getRootCauseMessage(ex)));
    	errorDTO.setCode(getExceptionCode(ex));
    	
        return Response.status(getStatusType(ex).getStatusCode())
                .entity(errorDTO)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
    private String getExceptionCode(Throwable ex) {
    	if(ex instanceof FieldRequiredException) {
    		return FieldRequiredException.getCode();
    	} else if(ex instanceof TaskNotFoundException) {
    		return TaskNotFoundException.getCode();
    	}
    	return "INTERNAL_ERROR";
    }

    private Response.StatusType getStatusType(Throwable ex) {
        if (ex instanceof WebApplicationException) {
            return((WebApplicationException)ex).getResponse().getStatusInfo();
        } else if(ex instanceof BadRequestException) {
        	return Response.Status.BAD_REQUEST;
        } else if(ex instanceof NotFoundException) {
        	return Response.Status.NOT_FOUND;
        } else if(ex instanceof NotAuthorizedException) {
        	return Response.Status.UNAUTHORIZED;
        } else if(ex instanceof ForbiddenException) {
        	return Response.Status.FORBIDDEN;
        } else if(ex instanceof NotAllowedException) {
        	return Response.Status.METHOD_NOT_ALLOWED;
        } else if(ex instanceof NotAcceptableException) {
        	return Response.Status.NOT_ACCEPTABLE;
        } else if(ex instanceof NotSupportedException) {
        	return Response.Status.UNSUPPORTED_MEDIA_TYPE;
        } else {
            return Response.Status.INTERNAL_SERVER_ERROR;
        }
    }
}