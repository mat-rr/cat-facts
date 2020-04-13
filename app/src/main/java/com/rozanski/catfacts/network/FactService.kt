package com.rozanski.catfacts.network

import com.rozanski.catfacts.network.FactResponse.FactResponse
import retrofit2.http.GET
import io.reactivex.Observable

interface FactService {
    @GET("facts/")
    fun getAllFacts(): Observable<FactResponse>
}