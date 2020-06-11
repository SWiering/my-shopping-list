package com.simon.shoppinglist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simon.shoppinglist.R
import com.simon.shoppinglist.model.ListWithItems
import com.simon.shoppinglist.model.ShoppingList
import com.simon.shoppinglist.model.ShoppingListItem
import com.simon.shoppinglist.ui.adapters.ShoppingListItemAdapter
import kotlinx.android.synthetic.main.activity_add_list.*

const val EXTRA_LIST = "EXTRA_LIST"

class AddListActivity : AppCompatActivity() {

    private val shoppingListItems = arrayListOf<ShoppingListItem>()
    private val shoppingListItemAdapter =
        ShoppingListItemAdapter(
            shoppingListItems
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_list)

        initViews()
        initListeners()
    }

    private fun initListeners() {
        btnAddEntry.setOnClickListener{ addEntryItem() }
        btnListCreate.setOnClickListener{ createList() }
    }

    private fun initViews() {
        rvShoppingListEntries.layoutManager = LinearLayoutManager(this@AddListActivity, RecyclerView.VERTICAL, false)
        rvShoppingListEntries.adapter = shoppingListItemAdapter

        createItemTouchHelper().attachToRecyclerView(rvShoppingListEntries)
    }

    private fun addEntryItem() {
        val listItem = ShoppingListItem("", 1)

        shoppingListItems.add(listItem)

        shoppingListItemAdapter.notifyDataSetChanged()
    }

    private fun createList() {
        // TODO: Add validation on empty fields
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

    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
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
