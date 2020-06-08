package com.simon.shoppinglist.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.simon.shoppinglist.R
import com.simon.shoppinglist.model.ListWithItems
import com.simon.shoppinglist.model.ShoppingListItem
import com.simon.shoppinglist.ui.adapters.ShoppingListCardAdapter
import com.simon.shoppinglist.ui.adapters.ShoppingListItemAdapter
import kotlinx.android.synthetic.main.activity_view_list.*

class ViewListActivity : AppCompatActivity() {

    private lateinit var listWithItems: ListWithItems

    private val listItems = arrayListOf<ShoppingListItem>()
    private val listItemsAdapter = ShoppingListCardAdapter(listItems)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_list)

        initViews()
    }

    @SuppressLint("WrongConstant")
    private fun initViews() {
        listWithItems = intent.extras?.getParcelable(EXTRA_LIST)!!

        tvShoppingListTitle.text = listWithItems.shoppingList.name

        listItems.addAll(listWithItems.shoppingListItems)

        rvShoppingListItems.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rvShoppingListItems.adapter = listItemsAdapter
    }
}
