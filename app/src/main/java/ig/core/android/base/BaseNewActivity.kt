@file:Suppress("DEPRECATION")

package ig.core.android.base

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import ig.core.android.R
import ig.core.android.utils.hideLoading
import ig.core.android.utils.onAlertMessage
import ig.core.android.utils.showLoading
import ig.core.android.utils.stateflow.BaseResponse

abstract class BaseNewActivity<B : ViewDataBinding, VM : BaseViewModel>: AppCompatActivity() {

    protected abstract val layoutId: Int @LayoutRes get
    protected abstract val viewModel: VM
    protected open lateinit var binding: B

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

    /**StatFlow or ShareFlow Request and Response controls */
    open fun handleFlowResponse(resource: BaseResponse<*>) {
        when (resource) {
            is BaseResponse.Loading -> {
                showLoading(0.3F)
                return
            }

            is BaseResponse.Success -> {
                hideLoading()
                return
            }

            is BaseResponse.Failure -> {
                hideLoading()
                onAlertMessage(this, resource.messageTitle, "")
                return
            }

            else -> Log.d("BaseActivity", "Empty....")
        }
    }


//    private var loadingViewParent: ViewGroup? = null
//    private  var loadingView: LoadingView? = null
//
//    open fun showLoading(alpha: Float? = 0.3f) {
//        loadingView = loadingView ?: LoadingView(this).show(this, alpha, loadingViewParent)
//    }
//
//    open fun hideLoading() {
//        loadingView?.let{
//            it.hide()
//            loadingView = null
//        }
//    }
}