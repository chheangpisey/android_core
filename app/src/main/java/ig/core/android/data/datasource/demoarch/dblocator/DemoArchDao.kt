package ig.core.android.data.datasource.demoarch.dblocator

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @author piseychheang
 * Created on 11/2/22 at 8:06 AM
 * Modified By piseychheang on 11/2/22 at 8:06 AM
 * File name: DemoArchDao.kt
 */

@Dao
interface DemoArchDao {

    /**
     * Select all user from the Users table.
     *
     * @return all user.
     */
    @Query("SELECT * FROM user_table")
    fun getUser():  List<User>

    /**
     * Insert a user in the database. If the task already exists, replace it.
     *
     * @param user the users to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: ArrayList<User>)

    /**
     * Delete all user.
     */
    @Query("DELETE FROM user_table")
    fun deleteUser()

}