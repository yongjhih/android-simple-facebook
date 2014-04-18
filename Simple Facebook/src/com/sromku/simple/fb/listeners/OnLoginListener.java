package com.sromku.simple.fb.listeners;

import android.app.Activity;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;

/**
 * On login/logout actions listener
 * 
 * @author sromku
 */
public class OnLoginListener implements IOnLoginListener {
    @Override
    public void onLogin() {
    }

    @Override
    public void onLogin(SimpleFacebook simpleFacebook) {
    }

    @Override
    public void onNotAcceptingPermissions(Permission.Type type) {
    }

    @Override
    public void onException(Throwable throwable) {
    }

    @Override
    public void onFail(String reason) {
    }

    @Override
    public void onThinking() {
    }
}
