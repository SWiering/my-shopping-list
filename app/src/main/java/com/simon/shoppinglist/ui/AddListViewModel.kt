package com.simon.shoppinglist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.simon.shoppinglist.model.services.SuggestionList
import com.simon.shoppinglist.services.SuggestionsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddListViewModel(application: Application) : AndroidViewModel(application){

    private val suggestionsRepository = SuggestionsRepository()

    val suggestions = MutableLiveData<SuggestionList>()

    val error = MutableLiveData<String>()

    fun getSuggestions() {
        suggestionsRepository.getSuggestions().enqueue(object : Callback<SuggestionList> {
            override fun onResponse(call: Call<SuggestionList>, response: Response<SuggestionList>) {
                if (response.isSuccessful) suggestions.value = response.body()
                else error.value = "An error occurred: ${response.errorBody().toString()}"
            }

            override fun onFailure(call: Call<SuggestionList>, t: Throwable) {
                error.value = t.message
            }
        })
    }

}