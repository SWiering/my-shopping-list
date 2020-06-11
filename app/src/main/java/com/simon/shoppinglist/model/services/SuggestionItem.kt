package com.simon.shoppinglist.model.services

import com.google.gson.annotations.SerializedName

data class SuggestionItem(
    @SerializedName("name")
    var name: String,

    @SerializedName("image")
    var image: String
){
    fun getImageUrl() = "https://c-wiering.nl/api/shopping-list/images/$image"
}
