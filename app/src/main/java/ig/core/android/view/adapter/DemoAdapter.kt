package ig.core.android.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import ig.core.android.base.BaseRecyclerView
import ig.core.android.data.datasource.demoarch.dblocator.User
import ig.core.android.databinding.ItemUserBinding

/**
 * @author piseychheang
 * Created on 11/23/22 at 2:09 PM
 * Modified By piseychheang on 11/23/22 at 2:09 PM
 * File name: DemoAdapter.kt
 */
class DemoAdapter
    (item: ArrayList<User>, private val onItemClick: (item: User) -> Unit): BaseRecyclerView<ItemUserBinding, User>() {

    init {
        setItems(item)
    }

    override fun layout(inflater: LayoutInflater, parent: ViewGroup): ItemUserBinding {
        return ItemUserBinding.inflate(inflater, parent, false)
    }

    @SuppressLint("SetTextI18n")
    override fun bindView(context: Context, binding: ItemUserBinding, item: User, position: Int) {
        binding.root.setOnClickListener { onItemClick(item) }
        binding.txtName.text = "${item.first_name} ${item.last_name}"
        binding.txtEmail.text = item.email.toString()
    }
}