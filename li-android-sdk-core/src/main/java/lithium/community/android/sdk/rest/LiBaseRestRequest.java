/*
 * LiBaseRestRequest.java
 * Created on Dec 27, 2016
 *
 * Copyright 2016 Lithium Technologies, Inc.
 * San Francisco, California, U.S.A.  All Rights Reserved.
 *
 * This software is the  confidential and proprietary information
 * of  Lithium  Technologies,  Inc.  ("Confidential Information")
 * You shall not disclose such Confidential Information and shall
 * use  it  only in  accordance  with  the terms of  the  license
 * agreement you entered into with Lithium.
 */

package lithium.community.android.sdk.rest;

import android.content.Context;
import android.text.TextUtils;

import org.w3c.dom.Text;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import lithium.community.android.sdk.utils.LiCoreSDKUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Base rest request to community
 */
public class LiBaseRestRequest {

    /**
     * application/json media type
     */
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * utf_8 charset
     */
    public static final String UTF_8 = StandardCharsets.UTF_8.name();
    private final RestMethod method;
    private final RequestBody requestBody;
    private final Map<String, String> additionalHttpHeaders;
    private final Map<String, String> queryParams;
    private String path;
    private Context context;
    private boolean isAuthenticatedRequest = false;

    public LiBaseRestRequest(Context context, RestMethod method, String path, RequestBody requestBody,
                             Map<String, String> additionalHttpHeaders, boolean isAuthenticatedRequest) {
        this.context = context;
        this.method = method;
        this.path = path;
        this.requestBody = requestBody;
        this.additionalHttpHeaders = additionalHttpHeaders;
        this.isAuthenticatedRequest = isAuthenticatedRequest;
        this.queryParams = new HashMap<>();
    }


    public LiBaseRestRequest(Context context, RestMethod method, RequestBody requestBody,
                             Map<String, String> additionalHttpHeaders, boolean isAuthenticatedRequest) {
        this.context = context;
        this.method = method;
        this.requestBody = requestBody;
        this.additionalHttpHeaders = additionalHttpHeaders;
        this.isAuthenticatedRequest = isAuthenticatedRequest;
        this.queryParams = new HashMap<>();
    }

    public RestMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public Map<String, String> getAdditionalHttpHeaders() {
        return additionalHttpHeaders;
    }

    //Checks whether request if from an authenticated user.
    public boolean isAuthenticatedRequest() {
        return isAuthenticatedRequest;
    }

    /**
     * This method segregates query parameters which may be present afte the 'q' param which takes in LIQL.
     *
     * @param queryParameters
     */
    public void addQueryParam(String queryParameters) {
        if (TextUtils.isEmpty(queryParameters)) {
            return;
        }
        if(queryParameters.toUpperCase().startsWith("SELECT ")) {
            if (queryParameters.contains("&")) {
                String[] queryParamsArr = queryParameters.split("&");
                queryParams.put("q",queryParamsArr[0]);
                for (int i=1;i<queryParamsArr.length;i++) {
                    String[] qParams = queryParamsArr[i].split("=");
                    String key = qParams[0];
                    String value = (qParams.length > 1)? qParams[1]: null;
                    queryParams.put(key, value);
                }
            } else {
                queryParams.put("q",queryParameters);
            }
        } else {
            String[] queryParamsArr = queryParameters.split("&");
            for (String temp: queryParamsArr) {
                String[] qParams = temp.split("=");
                String key = qParams[0];
                String value = (qParams.length > 1) ? qParams[1] : null;
                queryParams.put(key, value);
            }
        }
    }

    public void addRequestHeader(String header, String value) {
        additionalHttpHeaders.put(header, value);
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public Context getContext() {
        return context;
    }

    /**
     * Enumeration for all HTTP methods.
     */
    public enum RestMethod {
        GET, POST, PUT, DELETE, HEAD, PATCH
    }
}
