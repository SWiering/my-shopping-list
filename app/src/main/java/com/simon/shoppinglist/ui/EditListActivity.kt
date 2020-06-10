package com.simon.shoppinglist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simon.shoppinglist.R
import com.simon.shoppinglist.model.ListWithItems
import com.simon.shoppinglist.model.ShoppingListItem
import com.simon.shoppinglist.ui.adapters.ShoppingListItemAdapter
import kotlinx.android.synthetic.main.activity_add_list.btnAddEntry
import kotlinx.android.synthetic.main.activity_add_list.etTitle
import kotlinx.android.synthetic.main.activity_add_list.rvShoppingListEntries
import kotlinx.android.synthetic.main.activity_edit_list.*
import kotlinx.android.synthetic.main.activity_edit_list.btnSaveList
import kotlinx.android.synthetic.main.app_bar_main.*

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

    private fun initListeners() {
        btnAddEntry.setOnClickListener { addEntryItem() }
        btnSaveList.setOnClickListener{ saveList() }
    }

    private fun saveList() {
        myList.shoppingListItems = this.shoppingListItems
        myList.shoppingList.name = etTitle.text.toString()

        val resultIntent = Intent()
        resultIntent.putExtra(EDIT_LIST_REQUEST, myList)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun initViews() {
        myList = intent.extras?.getParcelable(EDIT_LIST_REQUEST)!!

        myList.let{
            etTitle.setText(it.shoppingList.name)
            shoppingListItems.addAll(it.shoppingListItems)
        }

        rvShoppingListEntries.layoutManager = LinearLayoutManager(this@EditListActivity, RecyclerView.VERTICAL, false)
        rvShoppingListEntries.adapter = shoppingListItemAdapter
    }

    private fun addEntryItem(name: String = "", quantity: Int = 1) {
        val listItem = ShoppingListItem(name, quantity)
        shoppingListItems.add(listItem)
        shoppingListItemAdapter.notifyDataSetChanged()
    }
}