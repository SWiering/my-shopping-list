package com.simon.shoppinglist.model.services

import com.google.gson.annotations.SerializedName

// Because the API contains of a list of items, read the list, then the items

data class SuggestionList(
    @SerializedName("items")
    var items: List<SuggestionItem>
)