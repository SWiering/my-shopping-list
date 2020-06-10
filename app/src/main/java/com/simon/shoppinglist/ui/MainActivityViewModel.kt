package com.simon.shoppinglist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.simon.shoppinglist.db.ShoppingListRepository
import com.simon.shoppinglist.model.ListWithItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel (application: Application) : AndroidViewModel(application){

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val shoppingListRepository = ShoppingListRepository(application.applicationContext)

    fun insertListWithItems(shoppingList: ListWithItems){
        ioScope.launch {
            shoppingListRepository.insertListWithItems(shoppingList)
        }
    }

    fun deleteAllShoppingLists() {
        ioScope.launch {
            shoppingListRepository.deleteAllLists()
        }
    }



    fun getListById(id: Long): LiveData<ListWithItems> = shoppingListRepository.getListById(id)
//    fun getListById(id: Long) {
//        ioScope.launch {
//            shoppingListRepository.getListById(id)
//        }
//    }

    fun updateList(myList: ListWithItems){
        ioScope.launch {
            shoppingListRepository.updateList(myList)
        }
    }
}