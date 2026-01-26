package com.mk.androidshowcase.base

import io.mockk.MockKAnnotations
import org.junit.jupiter.api.BeforeEach

abstract class BaseTest<ClassUnderTest> {
    abstract var classUnderTest: ClassUnderTest

    @BeforeEach
    fun setup() { // if we don't call below, we will get NullPointerException
        MockKAnnotations.init(this, relaxUnitFun = true)
        beforeEach()
    }

    abstract fun beforeEach()
}
