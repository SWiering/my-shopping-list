package com.simon.shoppinglist.ui.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simon.shoppinglist.R
import com.simon.shoppinglist.db.ShoppingListRepository
import com.simon.shoppinglist.model.db.ListWithItems
import com.simon.shoppinglist.ui.ViewListActivity
import kotlinx.android.synthetic.main.list_card.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val EXTRA_LIST = "EXTRA_LIST"

class ShoppingListAdapter(private val listWithItems: List<ListWithItems>) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

//    private val mainActivityViewModel: MainActivityViewModel by viewModels()
//    val model = homeViewModel
    /**
     * Creates and returns a ViewHolder object, inflating the layout called item_reminder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_card, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return listWithItems.size
    }

    // A way to create button onclick listeners in the rv
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.btnLook.setOnClickListener { viewList(holder, position) }
        holder.btnDelete.setOnClickListener { deleteList(holder, position) }

        holder.bind(listWithItems[position])
    }

    // function to actually delete the list from the rv and database
    private fun deleteList(holder:ViewHolder, position: Int) {

        val ioScope = CoroutineScope(Dispatchers.IO)
        val shoppingListRepository = ShoppingListRepository(holder.itemView.context)

        // Because the button is inside the recyclerview do it this way
        ioScope.launch {
            shoppingListRepository.deleteListWithItems(listWithItems[position])
        }
    }

    // Start the viewlist activity to view the shopping list
    private fun viewList(holder: ViewHolder, position: Int) {
        val intent = Intent(holder.itemView.context, ViewListActivity::class.java)
        intent.putExtra(EXTRA_LIST, listWithItems[position])
        holder.itemView.context.startActivity(intent)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnLook: Button = itemView.btnCardLook
        val btnDelete: Button = itemView.btnCardDelete

        @SuppressLint("WrongConstant")
        // initialize the items in the recyclerview
        fun bind(shoppingList: ListWithItems) {
            itemView.tvCardTitle.text = shoppingList.shoppingList.name


            itemView.rvShoppingListItems.layoutManager = LinearLayoutManager(itemView.context, LinearLayout.VERTICAL, false)
            itemView.rvShoppingListItems.adapter =
                ShoppingListCardAdapter(
                    shoppingList.shoppingListItems
                )
        }
    }
}

