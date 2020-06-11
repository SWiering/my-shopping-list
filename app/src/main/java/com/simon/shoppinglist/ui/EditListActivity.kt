package com.simon.shoppinglist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simon.shoppinglist.R
import com.simon.shoppinglist.model.db.ListWithItems
import com.simon.shoppinglist.model.db.ShoppingListItem
import com.simon.shoppinglist.ui.adapters.ShoppingListItemAdapter
import kotlinx.android.synthetic.main.activity_add_list.btnAddEntry
import kotlinx.android.synthetic.main.activity_add_list.etTitle
import kotlinx.android.synthetic.main.activity_add_list.rvShoppingListEntries
import kotlinx.android.synthetic.main.activity_edit_list.btnSaveList

class EditListActivity : AppCompatActivity() {

    private val shoppingListItems = arrayListOf<ShoppingListItem>()
    private val shoppingListItemAdapter =
        ShoppingListItemAdapter(
            shoppingListItems
        )

    private lateinit var myList: ListWithItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_list)

        initListeners()
        initViews()
    }

    // Initialize the bhtton listeners
    private fun initListeners() {
        btnAddEntry.setOnClickListener { addEntryItem() }
        btnSaveList.setOnClickListener{ saveList() }
    }

    private fun saveList() {
        // myList is the ListWithItems that gets sent back so it can be updated
        myList.shoppingListItems = this.shoppingListItems
        myList.shoppingList.name = etTitle.text.toString()

        // Create intent and set result
        val resultIntent = Intent()
        resultIntent.putExtra(EDIT_LIST_REQUEST, myList)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    // Initialize all the textviews and recyclerviews so the user can edit them
    private fun initViews() {
        // The edit activity always needs a listwithitems
        myList = intent.extras?.getParcelable(EDIT_LIST_REQUEST)!!

        // If data is found, set these in the text/recyclerviews
        myList.let{
            etTitle.setText(it.shoppingList.name)
            shoppingListItems.addAll(it.shoppingListItems)
        }

        rvShoppingListEntries.layoutManager = LinearLayoutManager(this@EditListActivity, RecyclerView.VERTICAL, false)
        rvShoppingListEntries.adapter = shoppingListItemAdapter

        // Add the touch helper so the user can delete on swipe
        createItemTouchHelper().attachToRecyclerView(rvShoppingListEntries)
    }

    // Function to add a new item to the list of entries, set the name to empty so the user can fill it in and set quantity to 1
    private fun addEntryItem(name: String = "", quantity: Int = 1) {
        if(shoppingListItems.size < MAX_ITEMS){
            val listItem =
                ShoppingListItem(name, quantity)
            shoppingListItems.add(listItem)
            shoppingListItemAdapter.notifyDataSetChanged()
        }
    }


    // Helper to delete an itemm when swiped to the left side
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
