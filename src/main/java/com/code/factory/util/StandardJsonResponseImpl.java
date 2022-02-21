package com.code.factory.util;

import java.util.HashMap;

public class StandardJsonResponseImpl implements StandardJsonResponse{

    private boolean                 success = false;

    private HashMap<String, String> messages;

    private HashMap<String, String> errors;

    private HashMap<String, Object> data;

    private int                     httpResponseCode;

    public StandardJsonResponseImpl(){

		this.messages = new HashMap<>();
		this.errors = new HashMap<>();
		this.data = new HashMap<>();
    }

    /**
     * @param success
     *            the success to set if success is false a default message and title is added
     */
    @Override
    public void setSuccess(final boolean success){
        this.success = success;
        if(!success){
			this.messages.put(StandardJsonResponse.DEFAULT_MSG_NAME_FIELD, StandardJsonResponse.DEFAULT_MSG_NAME_VALUE);
			this.messages.put(
					StandardJsonResponse.DEFAULT_MSG_TITLE_FIELD,
					StandardJsonResponse.DEFAULT_MSG_TITLE_VALUE);
        }
    }

    /**
     * @return the success
     */
    @Override
    public boolean isSuccess(){
        return this.success;
    }

    /**
     * @param messages
     *            the messages to set
     */
    @Override
    public void setMessages(final HashMap<String, String> messages){
        this.messages = messages;
    }

    /**
     * @return the messages
     */
    @Override
    public HashMap<String, String> getMessages(){
        return this.messages;
    }

    /**
     * @param errors
     *            the errors to set
     */
    @Override
    public void setErrors(final HashMap<String, String> errors){
        this.errors = errors;
    }

    /**
     * @return the errors
     */
    @Override
    public HashMap<String, String> getErrors(){
        return this.errors;
    }

    @Override
    public HashMap<String, Object> getData(){
        return this.data;
    }

    @Override
    public void setData(final HashMap<String, Object> data){
        this.data = data;
    }

    /**
     * @param success
     * @param title
     *            - message title
     * @param message
     *            -message body
     */
    @Override
    public void setSuccess(final boolean success, final String title, final String message){
        this.success = success;
		this.messages.put(StandardJsonResponse.DEFAULT_MSG_NAME_FIELD, (message == null || message.isEmpty()) ? "" : message);
		this.messages.put(StandardJsonResponse.DEFAULT_MSG_TITLE_FIELD, (title == null || title.isEmpty()) ? "" : title);
    }

    @Override
    public void setHttpResponseCode(final int code){
		this.httpResponseCode = code;
    }

    @Override
    public int getHttpResponseCode(){
        return this.httpResponseCode;
    }
}