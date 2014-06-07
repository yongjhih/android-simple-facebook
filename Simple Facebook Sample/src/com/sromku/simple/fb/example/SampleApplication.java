package com.sromku.simple.fb.example;

import android.app.Application;

import com.facebook.SessionDefaultAudience;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.example.utils.SharedObjects;
import com.sromku.simple.fb.utils.Logger;

public class SampleApplication extends Application {
	private static final String APP_ID = "728615400528729";
	private static final String APP_NAMESPACE = "sromkuapp_vtwo";

	@Override
	public void onCreate() {
		super.onCreate();
		SharedObjects.context = this;

		// set log to true
		Logger.DEBUG_WITH_STACKTRACE = true;

		// initialize facebook configuration
		Permission[] permissions = new Permission[] { 
				Permission.PUBLIC_PROFILE, 
				Permission.USER_GROUPS, 
				Permission.USER_LIKES, 
				Permission.USER_PHOTOS,
				Permission.USER_VIDEOS,
				Permission.USER_FRIENDS,
				Permission.USER_EVENTS,
				Permission.USER_VIDEOS,
				Permission.USER_RELATIONSHIPS,
				Permission.READ_STREAM, 
				Permission.PUBLISH_ACTION
				};

		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
			.setAppId(APP_ID)
			.setNamespace(APP_NAMESPACE)
			.setPermissions(permissions)
			.setDefaultAudience(SessionDefaultAudience.FRIENDS)
			.setAskForAllPermissionsAtOnce(false)
			.build();

		SimpleFacebook.setConfiguration(configuration);
	}
}
