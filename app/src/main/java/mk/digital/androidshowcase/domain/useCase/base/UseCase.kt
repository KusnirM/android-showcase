package mk.digital.androidshowcase.domain.useCase.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

abstract class UseCase<in Params, out Result> {

    protected abstract suspend fun run(params: Params): Result

    suspend operator fun invoke(params: Params): Result = withContext(Dispatchers.IO) {
        run(params)
    }
}

suspend operator fun <T> UseCase<None, T>.invoke(): T = invoke(None)

object None
