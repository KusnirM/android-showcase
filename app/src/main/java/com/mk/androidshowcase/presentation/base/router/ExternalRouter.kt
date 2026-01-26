package com.mk.androidshowcase.presentation.base.router

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExternalRouter @Inject constructor(
    @param:ApplicationContext private val context: Context
) : DialRouter by DialRouter.Impl(context),
    LinkRouter by LinkRouter.Impl(context),
    ShareRouter by ShareRouter.Impl(context),
    CopyRouter by CopyRouter.Impl(context),
    EmailRouter by EmailRouter.Impl(context),
    SettingsRouter by SettingsRouter.Impl(context)

