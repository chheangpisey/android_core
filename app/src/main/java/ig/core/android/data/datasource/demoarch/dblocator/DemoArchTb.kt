package ig.core.android.data.datasource.demoarch.dblocator

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author piseychheang
 * Created on 11/2/22 at 8:17 AM
 * Modified By piseychheang on 11/2/22 at 8:17 AM
 * File name: DemoArchTb.kt
 */

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val uId: Int?,
    val id: Int? = 0,
    val email: String? = "",
    val first_name: String? = "",
    val last_name: String? = ""
)
