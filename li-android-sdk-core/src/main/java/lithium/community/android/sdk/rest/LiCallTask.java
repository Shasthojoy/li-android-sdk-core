/*
 * LiCallTask.java
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


import java.io.IOException;

import lithium.community.android.sdk.exception.LiRestResponseException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LiCallTask {

    private final Call call;

    public LiCallTask(Call call) {
        this.call = call;
    }

    /**
     * Invokes the request immediately, and blocks until the response can be processed or is in
     * error.
     * <p>
     * <p>The caller may read the response body with the response's {@link Response#body} method.  To
     * facilitate connection recycling, callers should always {@link ResponseBody#close() close the
     * response body}.
     * <p>
     * <p>Note that transport-layer success (receiving a HTTP response code, headers and body) does
     * not necessarily indicate application-layer success: {@code response} may still indicate an
     * unhappy HTTP response code like 404 or 500.
     *
     * @throws IOException           if the request could not be executed due to cancellation, a connectivity
     *                               problem or timeout. Because networks can fail during an exchange, it is possible that the
     *                               remote server accepted the request before the failure.
     * @throws IllegalStateException when the call has already been executed.
     */
    public LiBaseResponse execute() throws LiRestResponseException {
        try {
            return new LiBaseResponse(this.call.execute());
        } catch (IOException e) {
            throw LiRestResponseException.networkError(e.getMessage());
        }
    }

    /**
     * Schedules the request to be executed at some point in the future.
     * <p>
     * <p>The {@link OkHttpClient#dispatcher dispatcher} defines when the request will run: usually
     * immediately unless there are several other requests currently being executed.
     * <p>
     * <p>This client will later call back {@code responseCallback} with either an HTTP response or a
     * failure exception.
     *
     * @throws IllegalStateException when the call has already been executed.
     */
    public void enqueue(final LiBaseRestRequest request, final LiAsyncRequestCallback responseCallback) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                responseCallback.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    responseCallback.onSuccess(request, new LiBaseResponse(response));
                } catch (LiRestResponseException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        });
    }
}
