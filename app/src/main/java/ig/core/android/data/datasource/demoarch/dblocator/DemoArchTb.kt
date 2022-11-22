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
    val email: String? = ""
)

//data class DemoArchTb @JvmOverloads constructor(
//    @PrimaryKey @ColumnInfo(name = "id") var id: Int = 0,
//    @ColumnInfo(name = "email") var email: String = ""
//) {
//    operator fun set(id: Int, email: String, value: DemoArchTb) {
//        this.id = id
//        this.email = email
//    }
//
//    val nameUser: String
//        get() = email.ifEmpty { "N/A" }
//
//
//    val isActive
//        get() = !isCompleted
//
//    val isEmpty
//        get() = email.isEmpty() || id == 0
//}