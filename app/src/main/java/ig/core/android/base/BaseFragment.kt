package ig.core.android.base

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ig.core.android.R
import ig.core.android.service.model.custom.ResourceResponse
import ig.core.android.service.model.custom.StateFlowResponse
import ig.core.android.utils.LoadingView
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton

/****
 *
 * Created by ORN TONY on 01/01/19.
 *
 */

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    protected abstract val mLayoutId: Int @LayoutRes get
    protected open lateinit var viewModel: VM
    protected open lateinit var mBinding: B

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, mLayoutId, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_background))
        viewModel = ViewModelProvider(requireActivity(), getViewModelFactory())[getViewModelClass()]
        initView()
    }

    abstract fun getViewModelClass(): Class<VM>
    abstract fun getViewModelFactory(): ViewModelProvider.Factory
    abstract fun initView()

    private val mLoadingViewParent: ViewGroup? = null
    private var loadingView: LoadingView? = null

    open fun showLoading(alpha: Float? = 0.3f) {
        loadingView = loadingView ?: LoadingView(requireContext()).show(requireActivity(), alpha, mLoadingViewParent)
    }

    open fun hideLoading() {
        loadingView?.let{
            it.hide()
            loadingView = null
        }
    }

    private fun onAlertError(message: String = "Error message") {
        activity?.runOnUiThread {
            activity?.alert(message) {
                okButton { }
            }?.show()
        }
    }

    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_ESCAPE || keyCode == KeyEvent.KEYCODE_BACK) {
                onBack()
                return true
            }
        }
        return false
    }

    open fun handleResponseRequest(resource: ResourceResponse<*>) {
        when (resource) {
            is ResourceResponse.Loading -> {
                showLoading()
                return
            }
            is ResourceResponse.Success -> {
                hideLoading()
                return
            }
            is ResourceResponse.ApiError -> {
                handleResponseError(resource)
                hideLoading()
                return
            }
            is ResourceResponse.UnknownError -> {
                hideLoading()
                onAlertError(resource.messageTitle!!)
                return
            }
            is ResourceResponse.NetworkError -> {
                hideLoading()
                onAlertError(resource.messageTitle!!)
                return
            }
        }
    }

    open fun handleStateFlowResponse(resource: StateFlowResponse<*>) {
        when (resource) {
            is StateFlowResponse.Loading -> {
                showLoading(0.3F)
                return
            }

            is StateFlowResponse.Success -> {
                Log.d("Login", "Success....")
                hideLoading()
                return
            }

            is StateFlowResponse.Failure -> {
                Log.d("Login", "Failure....${resource.msg}")
                hideLoading()
                onAlertError(resource.msg)
                return
            }
            else -> Log.d("Login", "Empty....")
        }
    }


    private fun isLoading(isLoading: Boolean) {
        if (isLoading) {
            showLoading()
        }
    }

    private fun isHideLoading(isLoading: Boolean) {
        if (isLoading) {
            hideLoading()
        }
    }

    open fun handleResponseError(resource: ResourceResponse<*>) {
        onAlertError(resource.messageTitle.toString())
    }

    open fun onBack() {}
}