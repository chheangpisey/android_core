package ig.core.android.view.viewholder

import android.content.Context
import ig.core.android.base.BaseViewHolder
import ig.core.android.databinding.ItemSearchBinding
import ig.core.android.service.model.PostsModel

class SearchViewHolder(private val itemSearchBinding: ItemSearchBinding) : BaseViewHolder<PostsModel>(itemSearchBinding.root) {
    override fun bind(context: Context, item: PostsModel, position: Int) {
        itemSearchBinding.itemSearch = item
    }
}