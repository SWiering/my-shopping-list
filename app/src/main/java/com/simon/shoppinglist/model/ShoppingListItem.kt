package com.simon.shoppinglist.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

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