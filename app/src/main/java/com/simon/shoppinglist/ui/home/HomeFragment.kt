package com.simon.shoppinglist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simon.shoppinglist.R
import com.simon.shoppinglist.model.ListWithItems
import com.simon.shoppinglist.ui.adapters.ShoppingListAdapter

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        initRecycler(root)
        observeViewModel()

        return root
    }


    private val shoppingLists = arrayListOf<ListWithItems>()
    private val shoppingListAdapter = ShoppingListAdapter(shoppingLists)

    private fun initRecycler(root: View) {
        val listWithItems: RecyclerView = root.findViewById(R.id.rvShoppingLists)
        listWithItems.layoutManager = LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)
        listWithItems.adapter = shoppingListAdapter
    }

    private fun observeViewModel() {
        homeViewModel.shoppingList.observe(viewLifecycleOwner, Observer { theLists ->
            this.shoppingLists.clear()
            this.shoppingLists.addAll(theLists)
            shoppingListAdapter.notifyDataSetChanged()
        })
    }
}
