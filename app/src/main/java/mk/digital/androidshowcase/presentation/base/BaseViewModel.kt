package mk.digital.androidshowcase.presentation.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import mk.digital.androidshowcase.domain.exceptions.base.BaseException
import mk.digital.androidshowcase.domain.exceptions.base.UnknownException
import mk.digital.androidshowcase.domain.useCase.analytics.TrackScreenUseCase
import mk.digital.androidshowcase.util.Logger

@EntryPoint
@InstallIn(SingletonComponent::class)
interface BaseViewModelEntryPoint {
    fun trackScreenUseCase(): TrackScreenUseCase
    fun logger(): Logger
}

abstract class BaseViewModel<STATE : Any>(
    application: Application,
    defaultState: STATE,
) : AndroidViewModel(application) {

    private val entryPoint: BaseViewModelEntryPoint by lazy {
        EntryPointAccessors.fromApplication(application, BaseViewModelEntryPoint::class.java)
    }

    private val trackScreenUseCase: TrackScreenUseCase by lazy { entryPoint.trackScreenUseCase() }
    protected val logger: Logger by lazy { entryPoint.logger() }

    protected val tag = this::class.simpleName

    private val _state: MutableStateFlow<STATE> = MutableStateFlow(defaultState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    private val _navEvent = MutableSharedFlow<NavEvent>()
    val navEvent: SharedFlow<NavEvent> = _navEvent.asSharedFlow()

    private val scope get() = viewModelScope

    init {
        logScreenName()
        loadInitialData()
    }

    protected open fun loadInitialData() {}

    protected fun navigate(event: NavEvent) {
        viewModelScope.launch { _navEvent.emit(event) }
    }

    protected fun newState(stateCopy: (STATE) -> STATE) {
        _state.value = stateCopy(_state.value)
    }

    protected fun requireState(block: (STATE) -> Unit): Unit = block(_state.value)

    protected fun requireState(): STATE = _state.value

    protected fun logScreenName() {
        val screenName = tag?.removeSuffix("ViewModel") ?: return
        logger.d("Screen: $screenName")
        trackScreenUseCase(screenName)
    }

    @Suppress("TooGenericExceptionCaught")
    protected fun <T> execute(
        action: suspend () -> T,
        onLoading: () -> Unit = {},
        onSuccess: (T) -> Unit = {},
        onError: (BaseException) -> Unit = {}
    ): Job = scope.launch {
        onLoading()
        try {
            onSuccess(action())
        } catch (e: BaseException) {
            logger.e("${tag}: ${e.message}", e)
            onError(e)
        } catch (e: Throwable) {
            logger.e("${tag}: ${e.message}", e)
            onError(UnknownException(e))
        }
    }

    @Suppress("TooGenericExceptionCaught")
    protected fun <T> observe(
        onStart: (suspend () -> Unit)? = null,
        flow: Flow<T>,
        onEach: (T) -> Unit,
        onError: (BaseException) -> Unit = {}
    ): Job = scope.launch {
        try {
            onStart?.invoke()
        } catch (e: BaseException) {
            logger.e("${tag}: ${e.message}", e)
            onError(e)
            return@launch
        } catch (e: Throwable) {
            logger.e("${tag}: ${e.message}", e)
            onError(UnknownException(e))
            return@launch
        }
        flow.catch { e ->
            when (e) {
                is BaseException -> {
                    logger.e("${tag}: ${e.message}", e)
                    onError(e)
                }

                else -> {
                    logger.e("${tag}: ${e.message}", e)
                    onError(UnknownException(e))
                }
            }
        }.collect { onEach(it) }
    }
}

interface NavEvent
