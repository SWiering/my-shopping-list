package com.simon.shoppinglist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.simon.shoppinglist.db.ShoppingListRepository
import com.simon.shoppinglist.model.ListWithItems
import com.simon.shoppinglist.model.ShoppingList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel (application: Application) : AndroidViewModel(application){

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val shoppingListRepository = ShoppingListRepository(application.applicationContext)

    val shoppingList: LiveData<List<ListWithItems>> = shoppingListRepository.getAllShoppingLists()

    fun insertListWithItems(shoppingList: ListWithItems){
        ioScope.launch {
            shoppingListRepository.insertListWithItems(shoppingList)
        }
    }

    fun deleteShoppingList(shoppingList: ShoppingList){
        ioScope.launch {
            shoppingListRepository.deleteList(shoppingList)
        }
    }

    fun deleteAllShoppingLists() {
        ioScope.launch {
            shoppingListRepository.deleteAllLists()
        }
    }
}