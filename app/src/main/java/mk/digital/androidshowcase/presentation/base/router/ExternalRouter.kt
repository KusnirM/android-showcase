package mk.digital.androidshowcase.presentation.base.router

import android.content.Context


class ExternalRouter(private val context: Context) : DialRouter by DialRouter.Impl(context),
    LinkRouter by LinkRouter.Impl(context),
    ShareRouter by ShareRouter.Impl(context),
    CopyRouter by CopyRouter.Impl(context),
    EmailRouter by EmailRouter.Impl(context),
    SettingsRouter by SettingsRouter.Impl(context)

