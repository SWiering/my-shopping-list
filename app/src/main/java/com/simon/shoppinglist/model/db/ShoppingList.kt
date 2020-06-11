package com.simon.shoppinglist.model.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

// Properties of a shopping list

@Parcelize
@Entity(tableName = "shopping_list")
data class ShoppingList(
    @ColumnInfo(name = "name")
    var name: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "listId")
    var id: Long? = null
) : Parcelable
