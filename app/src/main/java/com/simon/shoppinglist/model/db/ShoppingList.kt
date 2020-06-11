package com.simon.shoppinglist.model.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "shopping_list")
data class ShoppingList(
    @ColumnInfo(name = "name")
    var name: String,

//    @Relation(parentColumn = "id", entityColumn = "list_id")
//    var items: List<ShoppingListItem>,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "listId")
    var id: Long? = null
) : Parcelable
