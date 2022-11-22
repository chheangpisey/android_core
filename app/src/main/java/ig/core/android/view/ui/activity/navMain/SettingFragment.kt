package ig.core.android.view.ui.activity.navMain

import android.provider.ContactsContract.Directory
import androidx.lifecycle.ViewModelProvider
import ig.core.android.R
import ig.core.android.base.BaseFragment
import ig.core.android.databinding.FragmentSettingBinding
import ig.core.android.di.Injection
import ig.core.android.viewmodel.FragViewModel
import java.nio.file.DirectoryStream
import java.nio.file.Path

class SettingFragment : BaseFragment<FragmentSettingBinding, FragViewModel>() {
    override val mLayoutId = R.layout.fragment_setting
    override fun getViewModelClass(): Class<FragViewModel> = FragViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory = Injection.provideMainViewModelFactory

    override fun initView() {

        splitString("data.datasource.demo")
        split("data.datasource.demo")
    }

    private fun splitString(str: String) {
        str.split(".").let {
            val pre = it.subList(0, it.lastIndex - 1)
            val mid = listOf(str.toLowerCase())
            val last = it.last()

            println("A1: $pre")
            println("A2: $mid")
            println("A3: $last")

        }
    }

    private fun split(str: String) {
        val destDir = str.split(".")
        println("B: $destDir")
    }

}