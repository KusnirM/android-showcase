package mk.digital.androidshowcase.presentation.base.ext

/**
 * Port of [InstantTaskExecutorRule] from rule to extension in order to work in junit5.
 */
//class InstantExecutorExtension : BeforeEachCallback, AfterEachCallback {
//
//    override fun beforeEach(context: ExtensionContext?) {
//        ArchTaskExecutor.getInstance().setDelegate(InstantTaskExecutor)
//    }
//
//    override fun afterEach(context: ExtensionContext?) {
//        ArchTaskExecutor.getInstance().setDelegate(null)
//    }
//
//    private object InstantTaskExecutor : TaskExecutor() {
//
//        override fun executeOnDiskIO(runnable: Runnable) = runnable.run()
//
//        override fun postToMainThread(runnable: Runnable) = runnable.run()
//
//        override fun isMainThread(): Boolean = true
//    }
//}