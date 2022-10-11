package ig.core.android.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import ig.core.android.R
import ig.core.android.service.model.custom.ResourceResponse
import ig.core.android.service.model.custom.StateFlowResponse
import ig.core.android.utils.AppConstant.STARTACTIVITYANIM_LEFT
import ig.core.android.utils.AppConstant.STARTACTIVITYANIM_RIGHT
import ig.core.android.utils.AppConstant.STARTACTIVITYANIM_TOP
import ig.core.android.utils.LoadingView
import kotlinx.android.synthetic.main.default_toolbar.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton

/****
 *
 * Created by ORN TONY on 01/01/19.
 *
 */

@Suppress("DEPRECATION")
@SuppressLint("Registered")
abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>: AppCompatActivity() {
    protected abstract val mLayoutId: Int @LayoutRes get
    protected open lateinit var mViewModel: VM
    protected open lateinit var mBinding: B
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.setBackgroundColor(ContextCompat.getColor(this, R.color.color_background))
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorAccent)

        mBinding = DataBindingUtil.setContentView(this, mLayoutId)
        mViewModel = ViewModelProvider(this, getViewModelFactory())[getViewModelClass()]
        initView()
    }

    abstract fun getViewModelClass(): Class<VM>
    abstract fun getViewModelFactory(): ViewModelProvider.Factory
    abstract fun initView()

    fun onSetUpGeneralToolbar(toolbarTitle: String) {
        setSupportActionBar(tbGeneralBackPress)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        tvToolbarTitle.text = toolbarTitle
    }

    fun onSetUpGeneralBackPressToolbar(toolbarTitle: String) {
        setSupportActionBar(tbGeneralBackPress)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_press_white_icon_24dp)

        tvToolbarTitle.text = toolbarTitle
    }

    fun onSetUpCrossBackPressToolbar(toolbarTitle: String) {
        setSupportActionBar(tbGeneralBackPress)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.cross_press_white_icon_24dp)

        tvToolbarTitle.text = toolbarTitle
    }

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

    private val mLoadingViewParent: ViewGroup? = null
    private var loadingView: LoadingView? = null

    open fun showLoading(alpha: Float? = 0.3f) {
        loadingView = loadingView ?: LoadingView(this).show(this, alpha, mLoadingViewParent)
    }

    open fun hideLoading() {
        loadingView?.let{
            it.hide()
            loadingView = null
        }
    }

    protected fun onAlertError(message: String = "Error message") {
        alert(message) {
            okButton { }
        }.show()
    }

    protected fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)
            mProgressDialog!!.setCanceledOnTouchOutside(false)
            mProgressDialog!!.setCancelable(false)
        }
        mProgressDialog!!.setMessage("Processing ....")
        if (!mProgressDialog!!.isShowing) {
            mProgressDialog!!.show()
        }
    }

    protected fun hideProgressDialog() {
        mProgressDialog!!.dismiss()
    }

    open fun startNewActivityRight(clz: Class<out Activity?>, isFinish: Boolean) {
        startNewActivity(clz, STARTACTIVITYANIM_RIGHT, isFinish, null)
    }

    open fun startNewActivityRight(clz: Class<out Activity?>, bundle: Bundle?, isFinish: Boolean) {
        startNewActivity(clz, STARTACTIVITYANIM_RIGHT, isFinish, bundle)
    }

    open fun startNewActivityLeft(clz: Class<out Activity?>, isFinish: Boolean) {
        startNewActivity(clz, STARTACTIVITYANIM_LEFT, isFinish, null)
    }

    open fun startNewActivityTop(clz: Class<out Activity?>, isFinish: Boolean) {
        startNewActivity(clz, STARTACTIVITYANIM_TOP, isFinish, null)
    }

    @Synchronized
    open fun startNewActivity(clz: Class<out Activity?>, amin: Int, isFinish: Boolean, bundle: Bundle?) {
        if (this.javaClass.simpleName != clz.simpleName) {
            val intent = Intent(this, clz)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            startActivity(intent)
            when (amin) {
                STARTACTIVITYANIM_LEFT -> overridePendingTransition(R.anim.activity_left_in, R.anim.activity_left_out)
                STARTACTIVITYANIM_RIGHT -> overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out)
                STARTACTIVITYANIM_TOP -> overridePendingTransition(R.anim.activity_top_in, R.anim.activity_top_out)
            }
            if (isFinish) {
                finish()
            }
        }
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

    /**
     * Testing StatFlow
     */
    open fun handleStateFlowResponse(resource: StateFlowResponse) {
        when (resource) {
            is StateFlowResponse.Loading -> {
                showLoading()
                return
            }

            is StateFlowResponse.Success<*> -> {
                hideLoading()
                return
            }

            is StateFlowResponse.Failure -> {
                hideLoading()
                onAlertError(resource.msg)
            }

            else -> {
                hideLoading()
                println("Error: $resource")
            }
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
}