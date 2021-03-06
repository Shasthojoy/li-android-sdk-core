package lithium.community.android.sdk.rest;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import lithium.community.android.sdk.model.LiBaseModel;
import lithium.community.android.sdk.model.response.LiBrowse;

/**
 * Wrapper for LiBaseResponse to DELETE response.
 * Created by kunal.shrivastava on 5/4/17.
 */

public class LiDeleteClientResponse implements LiClientResponse<LiBaseResponse> {
    private LiBaseResponse liBaseResponse;

    public LiDeleteClientResponse(final LiBaseResponse liBaseResponse) {
        this.liBaseResponse = liBaseResponse;
    }

    public LiBaseResponse getResponse() {
        return liBaseResponse;
    }

    public Map<LiBrowse, List<LiBaseModel>> getTransformedResponse() {
        return null;
    }

    @Override
    public String getStatus() {
        return liBaseResponse.getStatus();
    }

    @Override
    public String getMessage() {
        return liBaseResponse.getMessage();
    }

    @Override
    public int getHttpCode() {
        return liBaseResponse.getHttpCode();
    }

    @Override
    public JsonObject getJsonObject() {
        return liBaseResponse.getData();
    }
}
