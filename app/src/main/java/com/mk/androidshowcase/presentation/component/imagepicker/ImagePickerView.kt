package com.mk.androidshowcase.presentation.component.imagepicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mk.androidshowcase.presentation.component.camera.rememberCameraManager
import com.mk.androidshowcase.presentation.component.galery.rememberGalleryManager
import com.mk.androidshowcase.presentation.component.permission.PermissionType
import com.mk.androidshowcase.presentation.component.permission.PermissionView

@Composable
fun ImagePickerView(viewModel: ImagePickerViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val cameraManager = rememberCameraManager { result ->
        viewModel.onImageLoading()
        viewModel.onImageResult(result)
    }

    val galleryManager = rememberGalleryManager { result ->
        viewModel.onImageLoading()
        viewModel.onImageResult(result)
    }

    if (state.showOptionDialog) {
        ImageSourceOptionDialog(
            onDismissRequest = { viewModel.hideDialog() },
            onAction = { viewModel.onActionSelected(it) },
        )
    }

    when (state.action) {
        PickerAction.Camera -> PermissionView(
            permission = PermissionType.CAMERA,
            onDeniedDialogDismiss = { viewModel.resetAction() },
        ) {
            cameraManager.launch()
            viewModel.resetAction()
        }

        PickerAction.Gallery -> {
            LaunchedEffect(state.action) {
                galleryManager.launch()
                viewModel.resetAction()
            }
        }

        PickerAction.None -> Unit
    }
}
