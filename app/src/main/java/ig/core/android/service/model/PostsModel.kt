package ig.core.android.service.model

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostsModel(val id: String? = "", val title: String? = "", val url: String? = "") :
    Parcelable


data class PostResponse(
    val userId: Long,
    val id: Long,
    val title: String,
    val body: String
)