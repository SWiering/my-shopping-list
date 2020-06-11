package com.simon.shoppinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.simon.shoppinglist.model.db.ShoppingList
import com.simon.shoppinglist.model.db.ShoppingListItem

// Define 2 entities for the one to many relation
@Database(entities = [ShoppingList::class, ShoppingListItem::class], version = 1, exportSchema = false)
abstract class ShoppingListRoomDatabase : RoomDatabase() {

    abstract fun shoppingListDao(): ShoppingListDao

    companion object {
        private const val DATABASE_NAME = "SHOPPINGLIST_DATABASE"

        @Volatile
        private var shoppingListRoomDatabaseInstance: ShoppingListRoomDatabase? = null

        fun getDatabase(context: Context): ShoppingListRoomDatabase? {
            if (shoppingListRoomDatabaseInstance == null) {
                synchronized(ShoppingListRoomDatabase::class.java) {
                    if (shoppingListRoomDatabaseInstance == null) {
                        shoppingListRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            ShoppingListRoomDatabase::class.java, DATABASE_NAME
                        ).build()
                    }
                }
            }
            return shoppingListRoomDatabaseInstance
        }
    }
}