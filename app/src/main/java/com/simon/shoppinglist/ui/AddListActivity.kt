package com.simon.shoppinglist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simon.shoppinglist.R
import com.simon.shoppinglist.model.db.ListWithItems
import com.simon.shoppinglist.model.db.ShoppingList
import com.simon.shoppinglist.model.db.ShoppingListItem
import com.simon.shoppinglist.model.services.SuggestionItem
import com.simon.shoppinglist.ui.adapters.ShoppingListItemAdapter
import com.simon.shoppinglist.ui.adapters.SuggestionAdapter
import kotlinx.android.synthetic.main.activity_add_list.*

const val EXTRA_LIST = "EXTRA_LIST"

class AddListActivity : AppCompatActivity() {

    private val shoppingListItems = arrayListOf<ShoppingListItem>()
    private val shoppingListItemAdapter =
        ShoppingListItemAdapter(
            shoppingListItems
        )

    private lateinit var viewModel: AddListViewModel

    private var sugItems = arrayListOf<SuggestionItem>()
    private val suggestionAdapter =
        SuggestionAdapter(
            sugItems, { suggestionItem -> onSuggestionClick(suggestionItem) }
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_list)

        initViewModel()
        initViews()
        initListeners()

    }

    // This is to intialize the viewmodel, not to already get the suggestions
    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AddListViewModel::class.java)

        viewModel.suggestions.observe(this, Observer {
            it?.items?.let {
                    suggestionItem ->
                        this.sugItems.clear()
                        this.sugItems.addAll(suggestionItem)
                        suggestionAdapter.notifyDataSetChanged()
            }
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    // Initialize the listeners for the buttons
    private fun initListeners() {
        btnAddEntry.setOnClickListener{ addEntryItem() }
        btnListCreate.setOnClickListener{ createList() }
    }

    // Initialize the recyclerviews and get suggestions from the apis into the rv
    private fun initViews() {
        rvShoppingListEntries.layoutManager = LinearLayoutManager(this@AddListActivity, RecyclerView.VERTICAL, false)
        rvShoppingListEntries.adapter = shoppingListItemAdapter

        rvSuggestions.layoutManager = LinearLayoutManager(this@AddListActivity, RecyclerView.HORIZONTAL, false)
        rvSuggestions.adapter = suggestionAdapter

        createItemTouchHelper().attachToRecyclerView(rvShoppingListEntries)

        viewModel.getSuggestions()
    }

    // Create a new entry for the shopping list
    private fun addEntryItem(name: String = "", quantity: Int = 1) {
        if(shoppingListItems.size < MAX_ITEMS){
            val listItem = ShoppingListItem(name, quantity)
            shoppingListItems.add(listItem)
            shoppingListItemAdapter.notifyDataSetChanged()
        }
    }

    // Initialize the data of the new ListWithItems object
    private fun createList() {
        val shopList = ShoppingList(
            etTitle.text.toString()
        )

        val listWithItems = ListWithItems(
            shopList,
            shoppingListItems
        )

        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_LIST, listWithItems)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    // When a suggestion is clicked, create an entry with its data
    private fun onSuggestionClick(suggestionItem: SuggestionItem){
        addEntryItem(suggestionItem.name, 1)
    }

    // helper so items can be removed from the list
    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                shoppingListItems.removeAt(position)
                shoppingListItemAdapter.notifyDataSetChanged()
            }
        }
        return ItemTouchHelper(callback)
    }
}
