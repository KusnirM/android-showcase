package com.mk.androidshowcase.presentation.component.permission

import androidx.annotation.StringRes
import com.mk.androidshowcase.R

enum class PermissionType(
    @get:StringRes val deniedMessage: Int,
    @get:StringRes val rationaleMessage: Int
) {
    CAMERA(R.string.camera_permission_denied, R.string.camera_permission_rationale),
    GALLERY(R.string.gallery_permission_denied, R.string.gallery_permission_rationale),
    LOCATION(R.string.location_permission_denied, R.string.location_permission_rationale),
    NOTIFICATION(R.string.notification_permission_denied, R.string.notification_permission_rationale)
}
