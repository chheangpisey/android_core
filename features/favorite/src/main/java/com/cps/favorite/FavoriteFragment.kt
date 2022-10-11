package com.cps.favorite

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.cps.favorite.databinding.FragmentFavoriteBinding
import com.google.android.play.core.splitinstall.SplitInstallException
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import ig.core.android.BuildConfig
import ig.core.android.base.BaseFragment
import ig.core.android.di.Injection
import ig.core.android.utils.AppConstant

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FragViewModel>() {
    override val mLayoutId = R.layout.fragment_favorite
    override fun getViewModelClass(): Class<FragViewModel> = FragViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory = Injection.provideMainViewModelFactory

    private val splitInstallManager: SplitInstallManager by lazy { SplitInstallManagerFactory.create(requireContext()) }

    override fun initView() {

        setupModuleDownload()
    }

    private fun setupModuleDownload() {

        //Creating Builder
        val homeModuleInstallRequest = SplitInstallRequest.newBuilder()
            .addModule(AppConstant.MODULE_HOME)
            .build()

        //Register split download with split manager
        splitInstallManager.registerListener {
            when (it.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    Toast.makeText(requireContext(), "Downloading", Toast.LENGTH_SHORT).show()
                }

                SplitInstallSessionStatus.INSTALLED -> {
                    Toast.makeText(
                        requireContext(),
                        "Module Download Completed",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent()
                    intent.setClassName(
                        BuildConfig.APPLICATION_ID,
                        "com.cps.home.HomeDetailActivity"
                    )
                    startActivity(intent)
                }

            }


        }

        //On Click Button to download the feature
        mBinding.btnDownloadModule.setOnClickListener {
            splitInstallManager.startInstall(homeModuleInstallRequest)
                .addOnFailureListener { exception ->
                    when ((exception as SplitInstallException).errorCode) {
                        SplitInstallErrorCode.API_NOT_AVAILABLE -> {
                            Toast.makeText(
                                requireContext(),
                                "Module Download Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }
    }

}