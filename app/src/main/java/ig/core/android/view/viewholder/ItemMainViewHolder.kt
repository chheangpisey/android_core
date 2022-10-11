package ig.core.android.view.viewholder

import android.content.Context
import ig.core.android.base.BaseViewHolder
import ig.core.android.databinding.ItemMainBinding
import ig.core.android.service.model.PostsModel

class ItemMainViewHolder(private val itemMainBinding: ItemMainBinding) : BaseViewHolder<PostsModel>(itemMainBinding.root) {
    override fun bind(context: Context, item: PostsModel) {
        itemMainBinding.itemMainViewModel = item
    }
}