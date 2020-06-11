package com.simon.shoppinglist.model.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

// Properties of a shoppinglistitem, has an id of itself and an id of a shopping list

@Parcelize
@Entity(tableName = "item")
data class ShoppingListItem (
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "quantity")
    var quantity: Int,

    @ColumnInfo(name = "shoppingListId")
    var shoppingListId: Long? = null,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "itemId")
    var id: Long? = null
): Parcelable