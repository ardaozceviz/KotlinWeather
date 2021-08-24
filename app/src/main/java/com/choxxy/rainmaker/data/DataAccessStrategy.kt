package com.cerpit.mobile.api

import com.cerpit.mobile.util.RateLimiter
import com.choxxy.rainmaker.data.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*

fun <T, A> performGetOperations(
    databaseQuery: () -> Flow<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit,
    rateLimiter: RateLimiter
): Flow<Resource<T>> =
    flow {
        coroutineScope {
            emit(Resource.loading())
            val source = databaseQuery.invoke().map {
                Resource.loading(it)
            }
            emit(source.first())

            if (rateLimiter.shouldFetch()) {
                val responseStatus = networkCall.invoke()
                if (responseStatus.status == Resource.Status.SUCCESS) {
                    saveCallResult(responseStatus.data!!)
                    emitAll(databaseQuery.invoke().map { Resource.success(it) })
                } else if (responseStatus.status == Resource.Status.ERROR) {
                    emit(Resource.error(responseStatus.message!!))
                }
            } else {
                emitAll(databaseQuery.invoke().map { Resource.success(it) })
            }
        }
    }

/**
 * Reduce boilerplate code for regular network operations
 */
fun <T> performGetOperation(networkCall: suspend () -> Resource<T>): Flow<Resource<T>> =
    flow {
        emit(Resource.loading())

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            emit(responseStatus)
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }.flowOn(Dispatchers.IO)

/**
 * Reduce boilerplate code for regular database operations
 */
fun <T> performDatabaseGetOperation(databaseQuery: () -> Flow<T>): Flow<Resource<T>> =
    flow {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitAll(source)
    }
