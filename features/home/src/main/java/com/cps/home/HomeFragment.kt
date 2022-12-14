package com.cps.home

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.cps.home.databinding.FragmentHomeBinding
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import ig.core.android.BuildConfig.APPLICATION_ID
import ig.core.android.base.BaseFragment
import ig.core.android.di.Injection
import ig.core.android.utils.AppConstant.MODULE_HOME

class HomeFragment : BaseFragment<FragmentHomeBinding, FragViewModel>() {
    override val mLayoutId = R.layout.fragment_home
    override fun getViewModelClass(): Class<FragViewModel> = FragViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory = Injection.provideDemoArchViewModelFactory(requireContext())

    private val splitInstallManager: SplitInstallManager by lazy { SplitInstallManagerFactory.create(requireContext()) }

    override fun initView() {

        //This feature use on-demand: so for testing need to upload an app into playstore
        setupModuleDownload()
    }

    private fun setupModuleDownload() {

        //Creating Builder
        val homeModuleInstallRequest = SplitInstallRequest.newBuilder()
            .addModule(MODULE_HOME)
            .build()

        //Register split download with split manager
        splitInstallManager.registerListener {
            when (it.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    mBinding.progressbar.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "Downloading", Toast.LENGTH_SHORT).show()
                }
                SplitInstallSessionStatus.INSTALLED -> {
                    mBinding.progressbar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Module Download Completed", Toast.LENGTH_SHORT).show()
                    val intent = Intent()
                    intent.setClassName(APPLICATION_ID, "com.cps.home.HomeDetailActivity")
                    startActivity(intent)
                }

                SplitInstallSessionStatus.FAILED -> {
                    mBinding.progressbar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Module Download Failed", Toast.LENGTH_SHORT).show()

                }
            }
        }

        //On Click Button to download the feature
        mBinding.btnDownloadModule.setOnClickListener {
            splitInstallManager.startInstall(homeModuleInstallRequest)
        }
    }
}