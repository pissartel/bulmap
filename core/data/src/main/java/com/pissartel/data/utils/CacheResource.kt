@file:Suppress("UNCHECKED_CAST")

package com.pissartel.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

class CacheResource<LOCAL>(initialValue: LOCAL? = null) {

    private val _data = MutableStateFlow(initialValue)
    val data = _data.asStateFlow()

    /**
     * inspired by : https://blog.devgenius.io/android-networking-and-database-caching-in-2020-mvvm-retrofit-room-flow-35b4f897d46a
     */
    suspend fun <REMOTE : Any> fetchResource(
        fetchFromLocal: suspend () -> Flow<LOCAL?> = { -> emptyFlow<LOCAL?>() },
        fetchFromRemote: suspend () -> REMOTE,
        mapRemoteToLocalResponse: (REMOTE) -> LOCAL = { remote -> remote as LOCAL },
        saveRemoteData: suspend (LOCAL) -> Unit = { Unit },
    ) {
        val apiResponse = fetchFromRemote()
        val mappedRemote = mapRemoteToLocalResponse(apiResponse)
        _data.emit(mappedRemote)
        saveRemoteData(mappedRemote)
    }

    suspend fun update(
        value: LOCAL, saveRemoteData: suspend (LOCAL) -> Unit = { Unit },
    ) {
        _data.emit(value)
        saveRemoteData(value)
    }

    suspend fun clear() {
        _data.emit(null)
    }
}
