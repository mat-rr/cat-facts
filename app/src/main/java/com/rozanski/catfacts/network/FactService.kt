package com.rozanski.catfacts.network

import com.rozanski.catfacts.network.FactResponse.FactResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface FactService {
    @GET("facts/")
    fun getAllFacts(): Observable<FactResponse>
}