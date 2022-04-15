package com.release.draglibrary

import android.app.Service
import android.view.WindowManager
import android.os.Build
import android.graphics.PixelFormat
import android.view.Gravity
import android.content.Intent
import android.graphics.Color
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.View.OnTouchListener
import android.view.MotionEvent
import android.view.View
import android.widget.Button

/**
 * 自定义可拖拽浮动到Window的Button
 * @author yancheng
 * @since 2022/04/15
 */
class FloatingWindowButton : Service() {
    companion object {
        var isStarted = false
    }
    private lateinit var windowManager: WindowManager
    private lateinit var layoutParams: WindowManager.LayoutParams
    private lateinit var button: Button
    private var isShow = false

    override fun onCreate() {
        super.onCreate()
        isStarted = true
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        initLayoutParams()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    private fun initLayoutParams() {
        layoutParams = WindowManager.LayoutParams()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        layoutParams.format = PixelFormat.RGBA_8888
        layoutParams.gravity = Gravity.LEFT or Gravity.TOP
        layoutParams.flags =
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        layoutParams.width = 500
        layoutParams.height = 100
        layoutParams.x = 300
        layoutParams.y = 300
    }

    fun showFloatingWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                button = Button(applicationContext)
                button.text = "浮动Button"
                button.setTextColor(Color.WHITE)
                button.setBackgroundColor(0xFF8273EF.toInt())
                windowManager.addView(button, layoutParams)
                button.setOnTouchListener(FloatingOnTouchListener())
                isShow = true
            }
        }
    }

    fun hideFloatingWindow(){
        if (isShow){
            windowManager.removeView(button)
            isShow = false
        }
    }

    private inner class FloatingOnTouchListener : OnTouchListener {
        private var x = 0
        private var y = 0
        override fun onTouch(view: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.rawX.toInt()
                    y = event.rawY.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    val nowX = event.rawX.toInt()
                    val nowY = event.rawY.toInt()
                    val movedX = nowX - x
                    val movedY = nowY - y
                    x = nowX
                    y = nowY
                    layoutParams.x = layoutParams.x + movedX
                    layoutParams.y = layoutParams.y + movedY
                    windowManager.updateViewLayout(view, layoutParams)
                }
            }
            return false
        }
    }


}