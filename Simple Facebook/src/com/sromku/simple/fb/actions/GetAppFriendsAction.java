package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.entities.Profile.Properties;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

/**
 * GetAppFriendsAction.
 *
 * SELECT uid FROM user WHERE is_app_user = '1' AND uid IN (SELECT uid2 FROM friend WHERE uid1 = me())
 * select uid, name, pic_square, is_app_user from user where uid in (select uid2 from friend where uid1 = me())
 */
public class GetAppFriendsAction extends GetAction<List<Profile>> {

    private Properties mProperties;

    public GetAppFriendsAction(SessionManager sessionManager) {
        super(sessionManager);
    }

    public void setProperties(Properties properties) {
        mProperties = properties;
    }

    @Override
    protected String getGraphPath() {
        return "fql";
    }

    @Override
    protected Bundle getBundle() {
        /* FIXME Convert to FQL or via Graph
        if (mProperties != null) {
            return mProperties.getBundle();
        }
        */
        //String fqlQuery = "SELECT uid, name, pic_square, is_app_user FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me())";
        String fqlQuery = "SELECT uid, name, pic_square FROM user WHERE is_app_user = '1' AND uid IN (SELECT uid2 FROM friend WHERE uid1 = me())";
        Bundle bundle = new Bundle();
        bundle.putString("q", fqlQuery);
        return bundle;
    }

    @Override
    protected List<Profile> processResponse(Response response) {
        List<GraphUser> graphUsers = Utils.typedListFromResponse(response, GraphUser.class);
        List<Profile> profiles = new ArrayList<Profile>(graphUsers.size());
        for (GraphUser graphUser : graphUsers) {
            profiles.add(Profile.create(graphUser));
        }
        return profiles;
    }

}
