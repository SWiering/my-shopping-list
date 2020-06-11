package com.simon.shoppinglist.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.simon.shoppinglist.R
import com.simon.shoppinglist.model.services.SuggestionItem
import kotlinx.android.synthetic.main.item_suggestion.view.*

class SuggestionAdapter(private val suggestions: List<SuggestionItem>, private val onClick: (SuggestionItem) -> Unit) : RecyclerView.Adapter<SuggestionAdapter.ViewHolder>(){

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_suggestion, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return suggestions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(suggestions[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener{
                onClick(suggestions[adapterPosition])
            }
        }

        fun bind (suggestionItem: SuggestionItem){
            itemView.tvSuggestionName.text = suggestionItem.name
            Glide.with(context).load(suggestionItem.getImageUrl()).into(itemView.ivSuggestionPicture)
        }
    }
}