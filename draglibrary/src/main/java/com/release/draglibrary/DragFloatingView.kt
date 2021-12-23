package com.release.draglibrary

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * 自定义可拖拽布局
 * 子View可点击
 * @author yancheng
 * @since 2021/12/20
 */
class DragFloatingView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    constructor(context: Context) : this(context, null)

    private var parentHeight = 0f
    private var parentWidth = 0f
    private var lastX = 0f
    private var lastY = 0f
    private var mIsAdsorption = false

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val rawX = event.rawX
        val rawY = event.rawY
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastX = rawX
                lastY = rawY
                if (parent != null) {
                    val parent = parent as ViewGroup
                    parentHeight = parent.height.toFloat()
                    parentWidth = parent.width.toFloat()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = rawX - lastX
                val dy = rawY - lastY
                lastX = rawX
                lastY = rawY
                return if (dx == 0.0f && dy == 0.0f)
                    super.onInterceptTouchEvent(event)
                else
                    true
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val rawX = event.rawX
        val rawY = event.rawY
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> return true
            MotionEvent.ACTION_MOVE -> {
                val dx = rawX - lastX
                val dy = rawY - lastY
                var x = x + dx
                var y = y + dy
                x = if (x < 0) 0f else if (x + width > parentWidth) parentWidth - width else x
                y = if (y < 0) 0f else if (y + height > parentHeight) parentHeight - height else y
                setX(x)
                setY(y)
                lastX = rawX
                lastY = rawY
            }
            MotionEvent.ACTION_UP -> {
                if (mIsAdsorption) {
                    if (rawX >= parentWidth / 2) {
                        //靠右吸附
                        animate().setInterpolator(DecelerateInterpolator())
                            .setDuration(500)
                            .xBy(parentWidth - x - width)
                            .start()
                    } else {
                        //靠左吸附
                        val oa = ObjectAnimator.ofFloat(this, "x", x, 0f)
                        oa.interpolator = DecelerateInterpolator()
                        oa.duration = 500
                        oa.start()
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 是否开启吸附功能
     */
    fun openAdsorption() {
        mIsAdsorption = true
    }
}