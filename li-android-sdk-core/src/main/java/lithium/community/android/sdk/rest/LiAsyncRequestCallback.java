/*
 * AsyncRequestCallback.java
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

import lithium.community.android.sdk.exception.LiRestResponseException;

/**
 * Request call back to use for activities that require async response processing.
 */

public interface LiAsyncRequestCallback<T> {

    /**
     * This call back is used when Async request is successful.
     * @param request {@link LiBaseRestRequest}
     * @param response Generic success response.
     * @throws LiRestResponseException {@link LiRestResponseException}
     */
    void onSuccess(LiBaseRestRequest request, T response) throws LiRestResponseException;

    /**
     *This call back is used when Async request is not successful.
     * @param exception {@link Exception}
     */
    void onError(Exception exception);
}
