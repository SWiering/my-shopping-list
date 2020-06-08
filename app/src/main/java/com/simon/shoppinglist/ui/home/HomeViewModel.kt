package com.simon.shoppinglist.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.simon.shoppinglist.db.ShoppingListRepository
import com.simon.shoppinglist.model.ListWithItems
import com.simon.shoppinglist.model.ShoppingList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel (application: Application) : AndroidViewModel(application){

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val shoppingListRepository = ShoppingListRepository(application.applicationContext)

    val shoppingList: LiveData<List<ListWithItems>> = shoppingListRepository.getAllShoppingLists()

    fun deleteShoppingList(shoppingList: ShoppingList){
        ioScope.launch {
            shoppingListRepository.deleteList(shoppingList)
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}