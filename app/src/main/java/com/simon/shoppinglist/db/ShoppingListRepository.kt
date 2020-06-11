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

    suspend fun insertList(shoppingList: ShoppingList) = shoppingListDao.insertList(shoppingList)

    suspend fun deleteList(shoppingList: ShoppingList) = shoppingListDao.deleteList(shoppingList)

    suspend fun updateList(shoppingList: ShoppingList) = shoppingListDao.updateList(shoppingList)

    @Transaction
    suspend fun updateList(myList: ListWithItems){
        myList.shoppingList.id?.let {
            val currentList = listById(it)

            for (theItem: ShoppingListItem in currentList.shoppingListItems){
                val foundItemInNewList = myList.shoppingListItems.filter { itemFromNewList ->
                    itemFromNewList.name.contains(theItem.name)
                }
                if (foundItemInNewList.isEmpty()){
                    delete(theItem)
                }
            }
        }

        updateList(myList.shoppingList)

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
        val listId = insert(shoppingList.shoppingList)

        for (theItem: ShoppingListItem in shoppingList.shoppingListItems){
            theItem.shoppingListId = listId
            insert(theItem)
        }
    }

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

    suspend fun deleteAllLists() = shoppingListDao.deleteAllLists()

    suspend fun listById(id: Long): ListWithItems = shoppingListDao.listById(id)

    fun getListById(id: Long): LiveData<ListWithItems> = shoppingListDao.getListById(id)
}