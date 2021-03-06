package com.simon.shoppinglist.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.simon.shoppinglist.R
import com.simon.shoppinglist.model.db.ListWithItems
import com.simon.shoppinglist.model.db.ShoppingListItem
import com.simon.shoppinglist.ui.adapters.ShoppingListCardAdapter
import kotlinx.android.synthetic.main.activity_view_list.*

const val EDIT_LIST_REQUEST = "EDIT_LIST"

class ViewListActivity : AppCompatActivity() {

    private lateinit var listWithItems: ListWithItems

    private val listItems = arrayListOf<ShoppingListItem>()
    private val listItemsAdapter = ShoppingListCardAdapter(listItems)

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_list)

        initViews()
        initListeners()
    }

    private fun initListeners() {
        fabEdit.setOnClickListener{ editList() }
    }

    companion object{
        const val EDIT_LIST_REQUEST_CODE = 100
    }

    private fun editList() {
        val editIntent = Intent(this, EditListActivity::class.java)
        editIntent.putExtra(EDIT_LIST_REQUEST, listWithItems)
        startActivityForResult(editIntent, EDIT_LIST_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            when (requestCode) {
                EDIT_LIST_REQUEST_CODE -> {
                    data?.let {safeData ->
                        val shoppingList = safeData.getParcelableExtra<ListWithItems>(EDIT_LIST_REQUEST)

                        shoppingList?.let {
                            // Update the list, also links and other data like the title
                                safeShoppingList ->
                            viewModel.updateList(safeShoppingList)

                            // If the list with items is edited, then initialize the view again
                            initViews(safeShoppingList.shoppingList.id!!)

                        } ?: run {
                            Log.e("Custom Debugging", "list is null")
                        }
                    } ?: run{
                        Log.e("Custom Debugging", "Null intent data recieved")
                    }
                }
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun initViews(theId: Long = 0) {

        // Initialize the recyclerview for the items
        rvShoppingListItems.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rvShoppingListItems.adapter = listItemsAdapter

        var myId = theId
        // Make with id instead of parcelable
        if(myId == 0.toLong()){
            // set new id here
            listWithItems = intent.extras?.getParcelable(EXTRA_LIST)!!
            myId = listWithItems.shoppingList.id!!
        }

        // Get new data from the currently selected list with items
        val myThing: LiveData<ListWithItems> =  viewModel.getListById(myId)

        // observe the live data
        myThing.observe(this, Observer { theList ->
            listWithItems = theList
            tvShoppingListTitle.text = theList.shoppingList.name
            this.listItems.clear()
            this.listItems.addAll(theList.shoppingListItems)
            listItemsAdapter.notifyDataSetChanged()
        })
    }
}
