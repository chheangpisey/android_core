@file:Suppress("DEPRECATION")

package ig.core.android.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import ig.core.android.utils.hideLoading
import ig.core.android.utils.showLoading
import ig.core.android.utils.stateflow.BaseResponse

abstract class BaseNewFragment<B : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    protected abstract val layoutId: Int @LayoutRes get
    protected abstract val viewModel: VM
    protected open lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    //The state created after onCreateView() run
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    /**Because based on lifecycle stateflow or sharedflow start collect while onCreate() state
     * and auto cancel onStop() onDestroy() state*/
//    override fun onCreate(savedInstanceState: Bundle?) {
//        networkView()
//
//        super.onCreate(savedInstanceState)
//    }

    abstract fun initView()

//    abstract fun networkView()


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
                return
            }

            else -> Log.d("BaseFragment", "Empty....")
        }
    }
}