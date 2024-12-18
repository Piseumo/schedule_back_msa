package com.example.notificationService.constant;

public enum NotificationType {

    FRIEND_REQUEST("FRIEND_REQUEST", "/friend/request"),   // 친구신청
    FRIEND_ACCEPT("FRIEND_ACCEPT", "/friend/accept"),     // 친구수락
    COMMENT("COMMENT", "/comments"),                      // 댓글
    MESSAGE("MESSAGE", "/messages"),                      // 쪽지
    FRIEND_NEW_POST("FRIEND_NEW_POST", "/friend/posts");  // 선택친구 새글

    private final String alias;  // 알림 유형 이름
    private final String path;   // 알림 대상 경로

    NotificationType(String alias, String path) {
        this.alias = alias;
        this.path = path;
    }

    public String getAlias() {
        return alias;
    }

    public String getPath() {
        return path;
    }
}