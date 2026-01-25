package mk.digital.androidshowcase.domain.useCase.base

suspend fun <T> test(
    given: () -> Unit = {},
    whenAction: suspend () -> T,
    then: suspend (T) -> Unit = {}
) {
    given()
    val whenActionResult = whenAction()
    then(whenActionResult)
}
