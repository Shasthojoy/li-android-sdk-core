package lithium.community.android.sdk.model.post;

import lithium.community.android.sdk.model.response.LiMessage;

/**
 * Created by shoureya.kant on 3/30/17.
 */

public class LiSubscriptionPostModel extends LiBasePostModel {

    private String type;
    private Target target;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public static class Target {
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String type;
        private String id;

    }
}
