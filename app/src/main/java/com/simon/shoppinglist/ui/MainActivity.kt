package com.simon.shoppinglist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.simon.shoppinglist.R
import com.simon.shoppinglist.model.db.ListWithItems
import kotlinx.android.synthetic.main.app_bar_main.*

const val MAX_ITEMS = 6

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val viewModel: MainActivityViewModel by viewModels()

    companion object {
        const val ADD_LIST_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        initListeners()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home
        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    // initialize the listeners ( mostly buttons )
    private fun initListeners(){
        btnSaveList.setOnClickListener{ startAddActivity() }
    }

    // Start the activity to add a new list with items
    private fun startAddActivity(){
        val intent = Intent(this, AddListActivity::class.java)
        startActivityForResult(intent,
            ADD_LIST_REQUEST_CODE
        )
    }

    // Function to remove all existing lists
    private fun removeLists(){
        viewModel.deleteAllShoppingLists()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            when (requestCode) {
                ADD_LIST_REQUEST_CODE -> {
                    data?.let {safeData ->
                        // If data is found insert it into the database
                        val shoppingList = safeData.getParcelableExtra<ListWithItems>(EXTRA_LIST)

                        shoppingList?.let {
                            safeShoppingList ->
                                viewModel.insertListWithItems(safeShoppingList)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.remove_history -> {
                removeLists()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
