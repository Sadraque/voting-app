package com.votingapp.controller;

import com.votingapp.domain.dto.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.net.URI;

public abstract class BaseController {

    protected String getRequestPath() {
        return getURI().toString();
    }

    protected UriComponents getUriComponents() {
        return ServletUriComponentsBuilder.fromCurrentRequest().build();
    }

    protected URI getURI() {
        return getUriComponents().toUri();
    }

    protected ResponseEntity<BaseResponse> ok(Object response, String message) {
        return response(response, message, HttpStatus.OK);
    }

    protected ResponseEntity<BaseResponse> ok(Object response) {
        return ok(response, null);
    }

    protected ResponseEntity<BaseResponse> created(Object response, String message) {
        return response(response, message, HttpStatus.CREATED);
    }

    protected ResponseEntity<BaseResponse> created(Object response) {
        return created(response, null);
    }

    protected ResponseEntity<BaseResponse> noContent() {
        return response(null, null, HttpStatus.NO_CONTENT);
    }

    private ResponseEntity<BaseResponse> response(Object response, String message, HttpStatus httpStatus) {
        BaseResponse baseResponse = new BaseResponse();

        baseResponse.setStatus(httpStatus.value());
        baseResponse.setMessage(StringUtils.isEmpty(message) ? httpStatus.getReasonPhrase() : message);
        baseResponse.setPath(getRequestPath());

        if (response != null) {
            baseResponse.setResponse(response);
            return ResponseEntity.status(httpStatus).body(baseResponse);
        }

        return ResponseEntity.status(httpStatus).build();
    }
}
