package com.simon.shoppinglist.services

import com.simon.shoppinglist.model.services.SuggestionList
import retrofit2.Call
import retrofit2.http.GET

interface SuggestionApiService {

    // GET the list of all items
    @GET("/api/shopping-list/index.php")
    fun getSuggestions(): Call<SuggestionList>
}
