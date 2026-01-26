package com.mk.androidshowcase.domain.model

data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val channel: NotificationChannel = NotificationChannel.GENERAL,
    val data: Map<String, String> = emptyMap(),
    val deepLink: String? = null
)
