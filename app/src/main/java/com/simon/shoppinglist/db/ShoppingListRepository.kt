package com.simon.shoppinglist.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Query
import androidx.room.Transaction
import com.simon.shoppinglist.model.ListWithItems
import com.simon.shoppinglist.model.ShoppingList
import com.simon.shoppinglist.model.ShoppingListItem
import java.lang.Exception

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


    fun getListById(id: Long): LiveData<ListWithItems> = shoppingListDao.getListById(id)
}