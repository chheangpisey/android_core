package ig.core.android.utils.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object BindingAdapterUtils {

    @BindingAdapter("imgUrl")
    @JvmStatic
    fun setImage(view: ImageView?, url: String?) {
        try {
            if (url != null) Picasso.get().load(url).into(view)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}