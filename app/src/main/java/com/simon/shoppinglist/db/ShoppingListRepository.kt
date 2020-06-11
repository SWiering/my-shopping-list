package com.simon.shoppinglist.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Transaction
import com.simon.shoppinglist.model.db.ListWithItems
import com.simon.shoppinglist.model.db.ShoppingList
import com.simon.shoppinglist.model.db.ShoppingListItem

class ShoppingListRepository(context: Context) {

    private var shoppingListDao: ShoppingListDao

    init {
        val shoppingListRoomDatabase= ShoppingListRoomDatabase.getDatabase(context)
        shoppingListDao = shoppingListRoomDatabase!!.shoppingListDao()
    }

    fun getAllShoppingLists(): LiveData<List<ListWithItems>> = shoppingListDao.getAllLists()

    // Put in a transaction because you want to make sure all queries within are executed
    @Transaction
    suspend fun updateList(myList: ListWithItems){
        myList.shoppingList.id?.let {

            // Get the current list from the database
            val currentList = listById(it)

            // Compare the given list with the database list and if an item is not found, remove that item entirely from the database
            for (theItem: ShoppingListItem in currentList.shoppingListItems){
                val foundItemInNewList = myList.shoppingListItems.filter { itemFromNewList ->
                    itemFromNewList.name.contains(theItem.name)
                }
                if (foundItemInNewList.isEmpty()){
                    delete(theItem)
                }
            }
        }

        // Update the shopping list itself (name only actually)
        update(myList.shoppingList)

        // Update all items, if they already existed (check for id) then update, otherwise; insert
        for(myItem: ShoppingListItem in myList.shoppingListItems){
            if(myItem.id == null){
                myItem.shoppingListId = myList.shoppingList.id
                insert(myItem)
            }else{
                update(myItem)
            }
        }
    }

    @Transaction
    suspend fun insertListWithItems(shoppingList: ListWithItems) {
        // Fist insert the shopping list, then get the id and set this as shoppinglistid for the items
        val listId = insert(shoppingList.shoppingList)

        // insert all the items
        for (theItem: ShoppingListItem in shoppingList.shoppingListItems){
            theItem.shoppingListId = listId
            insert(theItem)
        }
    }

    // To delete the list with all the items
    @Transaction
    suspend fun deleteListWithItems(shoppingList: ListWithItems){
        for(theItem: ShoppingListItem in shoppingList.shoppingListItems){
            delete(theItem)
        }
        delete(shoppingList.shoppingList)
    }

    private suspend fun insert(shoppingList: ShoppingList): Long =
        shoppingListDao.insertList(shoppingList)

    private suspend fun insert(shoppingListItem: ShoppingListItem) =
        shoppingListDao.insertItem(shoppingListItem)

    private suspend fun delete(shoppingListItem: ShoppingListItem) =
        shoppingListDao.deleteItem(shoppingListItem)

    private suspend fun delete(shoppingList: ShoppingList) =
        shoppingListDao.deleteList(shoppingList)

    private suspend fun update(shoppingListItem: ShoppingListItem) =
        shoppingListDao.updateItem(shoppingListItem)

    private suspend fun update(shoppingList: ShoppingList) =
        shoppingListDao.updateList(shoppingList)

    suspend fun deleteAllLists() = shoppingListDao.deleteAllLists()

    suspend fun listById(id: Long): ListWithItems = shoppingListDao.listById(id)

    fun getListById(id: Long): LiveData<ListWithItems> = shoppingListDao.getListById(id)
}