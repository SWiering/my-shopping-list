package com.simon.shoppinglist.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simon.shoppinglist.R
import com.simon.shoppinglist.model.db.ShoppingListItem
import kotlinx.android.synthetic.main.list_card_list_item.view.*

// Nothing really special to see here

class ShoppingListCardAdapter(private val shoppingListItems: List<ShoppingListItem>) : RecyclerView.Adapter<ShoppingListCardAdapter.ViewHolder>(){
    /**
     * Creates and returns a ViewHolder object, inflating the layout called item_reminder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_card_list_item, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return shoppingListItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(shoppingListItems[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind (shoppingListItem: ShoppingListItem){
            itemView.tvItemName.text = shoppingListItem.name
            itemView.tvItemQuantity.text = shoppingListItem.quantity.toString()
        }
    }
}