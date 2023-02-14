@file:Suppress("DEPRECATION")

package ig.core.android.base

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import ig.core.android.utils.stateflow.StateFlowResponse
import ig.core.android.R
import ig.core.android.service.model.custom.ResourceResponse
import ig.core.android.utils.LoadingView
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton

abstract class BaseNewActivity<B : ViewDataBinding, VM : BaseViewModel>: AppCompatActivity() {
    protected abstract val layoutId: Int @LayoutRes get
    protected abstract val viewModel: VM
    protected open lateinit var binding: B

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.setBackgroundColor(ContextCompat.getColor(this, R.color.color_background))
        binding = DataBindingUtil.setContentView(this, layoutId)

        initView()
    }

    abstract fun initView()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private var loadingViewParent: ViewGroup? = null
    private  var loadingView: LoadingView? = null

    open fun showLoading(alpha: Float? = 0.3f) {
        loadingView = loadingView ?: LoadingView(this).show(this, alpha, loadingViewParent)
    }

    open fun hideLoading() {
        loadingView?.let{
            it.hide()
            loadingView = null
        }
    }

    private fun onAlertError(message: String = "Error message") {
        alert(message) {
            okButton { }
        }.show()
    }

    protected fun showProgressDialog() {
        progressDialog.setMessage("Processing ....")
        if (!progressDialog.isShowing) {
            progressDialog.show()
        }
    }

    protected fun hideProgressDialog() {
        progressDialog.dismiss()
    }

    /**StatFlow Request and Response controls */
    open fun handleStateFlowResponse(resource: StateFlowResponse<*>) {
        when (resource) {
            is StateFlowResponse.Loading -> {
                showLoading(0.3F)
                return
            }

            is StateFlowResponse.Success -> {
                hideLoading()
                return
            }

            is StateFlowResponse.Failure -> {
                hideLoading()
                onAlertError("Failed to connect on: base-url")
                return
            }

            else -> Log.d("BaseActivity", "Empty....")
        }
    }

    open fun handleResponseError(resource: ResourceResponse<*>) {
        onAlertError(resource.messageTitle.toString())
    }
}