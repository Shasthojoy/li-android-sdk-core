/*
 * LiSDKManager.java
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

package lithium.community.android.sdk.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.util.NoSuchPropertyException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicBoolean;

import lithium.community.android.sdk.auth.LiAppCredentials;
import lithium.community.android.sdk.auth.LiAuthConstants;
import lithium.community.android.sdk.exception.LiRestResponseException;
import lithium.community.android.sdk.model.request.LiClientRequestParams;
import lithium.community.android.sdk.model.response.LiAppSdkSettings;
import lithium.community.android.sdk.queryutil.LiDefaultQueryHelper;
import lithium.community.android.sdk.rest.LiAsyncRequestCallback;
import lithium.community.android.sdk.rest.LiBaseRestRequest;
import lithium.community.android.sdk.rest.LiGetClientResponse;
import lithium.community.android.sdk.utils.LiCoreSDKUtils;
import lithium.community.android.sdk.utils.LiUUIDUtils;

import static lithium.community.android.sdk.utils.LiCoreSDKConstants.LI_DEFAULT_SDK_SETTINGS;
import static lithium.community.android.sdk.utils.LiCoreSDKConstants.LI_SHARED_PREFERENCES_NAME;

/**
 * Interface to Lithium community SDK. Provides entry point into community rest api v2 using
 * OAuth2 implementation.
 */
public final class LiSDKManager extends LiAuthManager {

    private static AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static LiSDKManager _sdkInstance;
    private final LiAppCredentials liAppCredentials;

    /**
     * Protected constructor.
     *
     * @param context          {@link Context}
     * @param liAppCredentials {@link LiAppCredentials}
     * @throws URISyntaxException {@link URISyntaxException}
     */
    private LiSDKManager(Context context, LiAppCredentials liAppCredentials) throws URISyntaxException {
        super(context);
        LiCoreSDKUtils.checkNotNull(context, liAppCredentials);
        this.liAppCredentials = liAppCredentials;
    }

    public static boolean isEnvironmentInitialized() {
        if (isInitialized.get() && _sdkInstance != null) {
            return true;
        }
        return false;
    }

    /**
     * Initializing liSDKManager class and initiates login flow. Also fetches SDK settings after login.
     *
     * @param context          {@link Context}
     * @param liAppCredentials {@link LiAppCredentials}
     * @return Instance of LiSDKManager
     * @throws URISyntaxException {@link URISyntaxException}
     */
    public static synchronized LiSDKManager init(final Context context, final LiAppCredentials liAppCredentials)
            throws URISyntaxException {
        LiCoreSDKUtils.checkNotNull(context, liAppCredentials);
        if (isInitialized.compareAndSet(false, true)) {
            LiDefaultQueryHelper.initHelper(context);
            _sdkInstance = new LiSDKManager(context, liAppCredentials);
        }
        LiCoreSDKUtils.checkNotNull(context, liAppCredentials);
        if (_sdkInstance.isUserLoggedIn()) {
            try {
                String clientId = LiUUIDUtils.toUUID(liAppCredentials.getClientKey().getBytes()).toString();
                LiClientRequestParams liClientRequestParams = new LiClientRequestParams.LiSdkSettingsClientRequestParams(context, clientId);
                LiClientManager.getSdkSettingsClient(liClientRequestParams).processAsync(
                        new LiAsyncRequestCallback<LiGetClientResponse>() {

                            @Override
                            public void onSuccess(LiBaseRestRequest request,
                                                  LiGetClientResponse response)
                                    throws LiRestResponseException {
                                if (response.getHttpCode() == 200) {
                                    Gson gson = new Gson();
                                    JsonArray items = response.getJsonObject().get("data")
                                            .getAsJsonObject().get("items").getAsJsonArray();
                                    if (!items.isJsonNull() && items.size() > 0) {
                                        LiAppSdkSettings liAppSdkSettings =
                                                gson.fromJson(items.get(0), LiAppSdkSettings.class);
                                        if (liAppSdkSettings != null) {
                                            SharedPreferences sharedPreferences = context.getSharedPreferences(
                                                    LI_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                                            sharedPreferences.edit().putString(LI_DEFAULT_SDK_SETTINGS,
                                                    liAppSdkSettings.getAdditionalInformation()).commit();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onError(Exception exception) {

                            }
                        });
            } catch (LiRestResponseException e) {
                Log.e(LiAuthConstants.LOG_TAG, e.getMessage());
            }
        }
        return _sdkInstance;
    }

    /**
     * Instance of this.
     */
    public static LiSDKManager getInstance() {
        if (_sdkInstance == null) {
            throw new NoSuchPropertyException("SDK not intialized. Call init method first");
        }
        return _sdkInstance;
    }

    /**
     * Returns community URI.
     */
    public Uri getCommunityUrl() {
        return liAppCredentials.getCommunityUri();
    }

    /**
     * Returns AppCredentials.
     */
    public LiAppCredentials getLiAppCredentials() {
        return liAppCredentials;
    }

    /**
     * Checks if it is deferred login.
     */
}
