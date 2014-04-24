package com.sromku.simple.fb.listeners;

import java.util.List;

public interface IOnInviteListener extends OnErrorListener {
    void onComplete(List<String> invitedFriends, String requestId);

    void onCancel();
}
