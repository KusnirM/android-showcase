package com.mk.androidshowcase.presentation.base

import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.mk.androidshowcase.base.BaseTest
import com.mk.androidshowcase.util.Logger

@ExperimentalCoroutinesApi
//@ExtendWith(InstantExecutorExtension::class)
abstract class BaseViewModelTest<ClassUnderTest> : BaseTest<ClassUnderTest>() {

    @MockK
    lateinit var logger: Logger

    override fun beforeEach() {

    }

    protected fun <VS, NE : NavEvent?> testViewModel(
        given: () -> Unit = {},
        whenAction: () -> Pair<VS, NE>,
        then: (viewState: VS, navEvent: NE) -> Unit,
    ) {
        given()
        val actionResult = whenAction()
        then(actionResult.first, actionResult.second)
    }

    protected fun <VS> testViewState(
        given: () -> Unit = {},
        whenAction: () -> VS,
        then: (VS) -> Unit,
    ) {
        given()
        val actionResult = whenAction()
        then(actionResult)
    }

    protected fun <NE : NavEvent?> testNavEvent(
        given: () -> Unit = {},
        whenAction: () -> NE,
        then: (NE) -> Unit,
    ) {
        given()
        val actionResult = whenAction()
        then(actionResult)
    }
}
