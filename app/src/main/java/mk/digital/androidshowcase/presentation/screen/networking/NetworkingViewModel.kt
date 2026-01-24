package mk.digital.androidshowcase.presentation.screen.networking

import dagger.hilt.android.lifecycle.HiltViewModel
import mk.digital.androidshowcase.domain.model.User
import mk.digital.androidshowcase.domain.useCase.GetUsersUseCase
import mk.digital.androidshowcase.domain.useCase.base.invoke
import mk.digital.androidshowcase.presentation.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class NetworkingViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel<NetworkingUiState>(NetworkingUiState()) {

    override fun loadInitialData() {
        fetchUsers()
    }

    fun fetchUsers() {
        execute(
            action = { getUsersUseCase() },
            onLoading = { newState { it.copy(isLoading = true, error = null) } },
            onSuccess = { users -> newState { it.copy(isLoading = false, users = users) } },
            onError = { error -> newState { it.copy(isLoading = false, error = error.message) } }
        )
    }

    fun refresh() {
        fetchUsers()
    }
}

data class NetworkingUiState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val error: String? = null
)
