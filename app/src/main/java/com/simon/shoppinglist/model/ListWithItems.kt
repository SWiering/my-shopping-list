package com.simon.shoppinglist.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListWithItems (
    @Embedded val shoppingList: ShoppingList,
    @Relation(
        parentColumn = "listId",
        entityColumn = "shoppingListId"
    )
    var shoppingListItems: List<ShoppingListItem>
): Parcelable