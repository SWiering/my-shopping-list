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
import com.simon.shoppinglist.model.ListWithItems
import com.simon.shoppinglist.model.ShoppingList
import com.simon.shoppinglist.ui.AddListActivity
import com.simon.shoppinglist.ui.ViewListActivity
import com.simon.shoppinglist.ui.home.HomeViewModel
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO: Rename this shit

        holder.btnLook.setOnClickListener { viewList(holder, position) }
        holder.btnDelete.setOnClickListener { deleteList(holder, position) }

        holder.bind(listWithItems[position])
    }

    private fun deleteList(holder:ViewHolder, position: Int) {

        val ioScope = CoroutineScope(Dispatchers.IO)
        val shoppingListRepository = ShoppingListRepository(holder.itemView.context)

        // TODO: Find a way to do this with a viewmodel?
        ioScope.launch {
            shoppingListRepository.deleteListWithItems(listWithItems[position])
        }
    }

    // TODO: Replace this so it can actually look up the list in an activity
    private fun viewList(holder: ViewHolder, position: Int) {
        val intent = Intent(holder.itemView.context, ViewListActivity::class.java)
        intent.putExtra(EXTRA_LIST, listWithItems[position])
        holder.itemView.context.startActivity(intent)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnLook: Button = itemView.btnCardLook
        val btnDelete: Button = itemView.btnCardDelete

        @SuppressLint("WrongConstant")
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

