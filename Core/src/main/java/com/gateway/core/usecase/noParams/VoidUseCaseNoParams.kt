package com.gateway.core.usecase.noParams

import com.gateway.core.base.Resource
import com.gateway.core.mapper.toResource
import com.gateway.core.mapper.toUnKnownError
import com.gateway.core.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class VoidUseCaseNoParams : BaseUseCase<Resource<Unit>>() {
    operator fun invoke(
        timeoutMs: Long = defaultTimeoutMs,
        dispatcher: CoroutineDispatcher = Dispatchers.Default
    ): Flow<Resource<Unit>> = flow {
        timeoutEmit(
            timeoutMs = timeoutMs,
            onStartedState = Resource.Loading,
            onEmptyState = Resource.Empty,
            onSuccessState = { Resource.Success(data = Unit) },
            onErrorState = { it.toResource() },
            suspendFunction = { doWork() }
        )
    }.flowOn(dispatcher)
        .catch { t -> emit(t.toUnKnownError().toResource()) }

    suspend fun executeSync() = doWork()

    protected abstract suspend fun doWork()
}
