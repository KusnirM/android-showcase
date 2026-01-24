package mk.digital.androidshowcase.presentation.component.imagepicker

import androidx.compose.ui.graphics.ImageBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import mk.digital.androidshowcase.presentation.base.BaseViewModel
import javax.inject.Inject

data class ImagePickerState(
    val showOptionDialog: Boolean = false,
    val action: PickerAction = PickerAction.None,
    val isLoading: Boolean = false,
    val imageBitmap: ImageBitmap? = null,
)

@HiltViewModel
class ImagePickerViewModel @Inject constructor() : BaseViewModel<ImagePickerState>(ImagePickerState()) {

    fun showDialog() {
        newState { it.copy(showOptionDialog = true) }
    }

    fun hideDialog() {
        newState { it.copy(showOptionDialog = false) }
    }

    fun onActionSelected(action: PickerAction) {
        newState { it.copy(showOptionDialog = false, action = action) }
    }

    fun onImageResult(result: ImageResult?) {
        newState { it.copy(action = PickerAction.None, isLoading = false, imageBitmap = result?.bitmap) }
    }

    fun onImageLoading() {
        newState { it.copy(isLoading = true) }
    }

    fun resetAction() {
        newState { it.copy(action = PickerAction.None) }
    }
}
