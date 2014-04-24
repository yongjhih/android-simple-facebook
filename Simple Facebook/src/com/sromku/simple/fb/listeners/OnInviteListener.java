package com.sromku.simple.fb.listeners;

import java.util.List;

public class OnInviteListener implements IOnInviteListener {
    @Override
    public void onComplete(List<String> invitedFriends, String requestId) {
    }

    @Override
    public void onCancel() {
    }

	@Override
	public void onException(Throwable throwable) {
	}

	@Override
	public void onFail(String reason) {
	}
}
