package com.simon.shoppinglist.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.simon.shoppinglist.model.ListWithItems
import com.simon.shoppinglist.model.ShoppingList
import com.simon.shoppinglist.model.ShoppingListItem

@Dao
interface ShoppingListDao {

    @Transaction
    @Query("SELECT * FROM shopping_list")
    fun getAllLists(): LiveData<List<ListWithItems>>

    @Insert
    suspend fun insertList(shoppingList: ShoppingList): Long

    @Insert
    suspend fun insertItem(shoppingListItem: ShoppingListItem)

    @Delete
    suspend fun deleteList(shoppingList: ShoppingList)

    @Delete
    suspend fun deleteItem(shoppingListItem: ShoppingListItem)

    @Update
    suspend fun updateItem(shoppingListItem: ShoppingListItem)

    @Update
    suspend fun updateList(shoppingList: ShoppingList)

    @Query("DELETE FROM shopping_list")
    suspend fun deleteAllLists()

    @Transaction
    @Query("SELECT * FROM shopping_list WHERE listId = :id")
    fun getListById(id: Long): LiveData<ListWithItems>
}