package com.example.vthai.sidecarprototype.utils

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/**
 * Using Kotlin's extension feature we add an extra method to Retrofit's Call class called 'await'
 * - This uses 'suspendCoroutine' to obtain the current continuation instance inside suspend functions and
 * suspends currently running coroutine.
 * - The keyword 'suspend(s)' used here insinuates that whichever thread this was called from will not be blocked
 * until the network callback is hit.  Once the callback is hit the continuation object allows us to
 * return the value retrieved from the call back to it's calling thread (usually main thread).
 */
suspend fun <T> Call<T>.await(): T {
    return suspendCoroutine { continuation ->
        this.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                call.cancel()
                continuation.resumeWithException(Exception("Error retrieving data"))
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                response.body()?.let {
                    continuation.resume(it)
                }
            }
        })
    }
}