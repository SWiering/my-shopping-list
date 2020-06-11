package com.simon.shoppinglist.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.simon.shoppinglist.model.db.ListWithItems
import com.simon.shoppinglist.model.db.ShoppingList
import com.simon.shoppinglist.model.db.ShoppingListItem

// The DAO for the shoppinglists and shoppinglistitems, converts queries to functions

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

    @Query("SELECT * FROM shopping_list WHERE listId = :id")
    suspend fun listById(id: Long): ListWithItems

}