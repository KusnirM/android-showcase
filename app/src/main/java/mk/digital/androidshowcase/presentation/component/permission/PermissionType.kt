package mk.digital.androidshowcase.presentation.component.permission

import mk.digital.androidshowcase.shared.generated.resources.Res
import mk.digital.androidshowcase.shared.generated.resources.camera_permission_denied
import mk.digital.androidshowcase.shared.generated.resources.camera_permission_rationale
import mk.digital.androidshowcase.shared.generated.resources.gallery_permission_denied
import mk.digital.androidshowcase.shared.generated.resources.gallery_permission_rationale
import mk.digital.androidshowcase.shared.generated.resources.location_permission_denied
import mk.digital.androidshowcase.shared.generated.resources.location_permission_rationale
import mk.digital.androidshowcase.shared.generated.resources.notification_permission_denied
import mk.digital.androidshowcase.shared.generated.resources.notification_permission_rationale
import org.jetbrains.compose.resources.StringResource

enum class PermissionType(val deniedMessage: StringResource, val rationaleMessage: StringResource) {
    CAMERA(Res.string.camera_permission_denied, Res.string.camera_permission_rationale),
    GALLERY(Res.string.gallery_permission_denied, Res.string.gallery_permission_rationale),
    LOCATION(Res.string.location_permission_denied, Res.string.location_permission_rationale),
    NOTIFICATION(Res.string.notification_permission_denied, Res.string.notification_permission_rationale)
}
