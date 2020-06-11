package com.simon.shoppinglist.services

// Available API calls converted to useful functions

class SuggestionsRepository {
    private val suggestionApi: SuggestionApiService = SuggestionApi.createApi()

    fun getSuggestions() = suggestionApi.getSuggestions()
}