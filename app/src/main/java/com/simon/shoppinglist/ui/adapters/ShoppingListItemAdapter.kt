package com.simon.shoppinglist.ui.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.simon.shoppinglist.R
import com.simon.shoppinglist.model.ShoppingListItem
import kotlinx.android.synthetic.main.list_entry_item.view.*


class ShoppingListItemAdapter(private var shoppingListItems: List<ShoppingListItem>) : RecyclerView.Adapter<ShoppingListItemAdapter.ViewHolder>(){
    //    /**
//     * Creates and returns a ViewHolder object, inflating the layout called item_reminder.
//     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_entry_item, parent, false)
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
        holder.nameInput.addTextChangedListener(NameChanger(position))
        holder.quantityInput.addTextChangedListener(QuantityChanger(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameInput: TextInputEditText = itemView.etShoppingItemName
        val quantityInput: TextInputEditText = itemView.etShoppingItemQuantity

        fun bind (shoppingListItem: ShoppingListItem){
            itemView.etShoppingItemName.setText(shoppingListItem.name)
            itemView.etShoppingItemQuantity.setText(shoppingListItem.quantity.toString())
        }
    }


    inner class NameChanger(private val listPosition: Int) : NameTextListener(){
        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            shoppingListItems[listPosition].name = charSequence.toString()
        }
    }
    inner class QuantityChanger(val listPosition: Int) : NameTextListener(){
        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            shoppingListItems[listPosition].quantity = charSequence.toString().toInt()
        }
    }
    // we make TextWatcher to be aware of the position it currently works with
    // this way, once a new item is attached in onBindViewHolder, it will
    // update current position MyCustomEditTextListener, reference to which is kept by ViewHolder
    abstract inner class NameTextListener : TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) { }

        override fun afterTextChanged(editable: Editable) { }
    }
}
