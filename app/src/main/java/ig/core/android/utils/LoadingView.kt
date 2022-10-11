package ig.core.android.utils

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.tuonbondol.keyboardutil.hideSoftKeyboard
import ig.core.android.R
import kotlinx.android.synthetic.main.view_loading.view.*

/****
 *
 * Created by ORN TONY on 01/01/19.
 *
 */

class LoadingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    fun show(activity: Activity, alpha: Float? = null, parent: ViewGroup? = null): LoadingView {
        activity.hideSoftKeyboard()
        viewBackground.alpha = alpha ?: 0.3f

        val layoutParams = MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layoutParams.setMargins(0, 0, 0, 0)
        parent?.addView(this, layoutParams) ?: run {
            activity.window.decorView
                    .findViewById<ViewGroup>(android.R.id.content)
                    .addView(this, layoutParams)
        }

        return this
    }

    fun hide() {
        removeFromSuperView()
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_loading, this)
    }
}

class CircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    var color
        get() = paint.color
        set(value) {
            paint.color = value
            invalidate()
        }

    private val paint = Paint()

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CircleView)
            color = typedArray.getColor(R.styleable.CircleView_color, Color.WHITE)
            typedArray.recycle()
        } ?: run {
            color = Color.WHITE
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (width / 2).toFloat(), paint)
    }
}

fun View.removeFromSuperView() {
    (parent as ViewGroup).removeView(this)
}