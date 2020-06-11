package com.simon.shoppinglist.model.services

import com.google.gson.annotations.SerializedName

data class SuggestionList(
    @SerializedName("items")
    var items: List<SuggestionItem>
)