package ig.core.android.data.datasource.demoarch.dblocator

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * @author piseychheang
 * Created on 11/2/22 at 8:27 AM
 * Modified By piseychheang on 11/2/22 at 8:27 AM
 * File name: DemoArchDatabase.kt
 */

private const val DATABASE_NAME = "Users.db"
@Database(entities = [User::class], version = 6, exportSchema = false)
abstract class DemoArchDatabase: RoomDatabase() {
    abstract fun user(): DemoArchDao

    companion object {
        @Volatile
        private var INSTANCE: DemoArchDatabase? = null

        fun getInstance(context: Context): DemoArchDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DemoArchDatabase::class.java, DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()
    }
}