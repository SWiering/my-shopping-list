package com.simon.shoppinglist.services

class SuggestionsRepository {
    private val suggestionApi: SuggestionApiService = SuggestionApi.createApi()

    fun getSuggestions() = suggestionApi.getSuggestions()
}