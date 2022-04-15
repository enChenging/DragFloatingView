package com.release.dragfloatingview

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.release.draglibrary.FloatingWindowButton
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 自定义可拖拽布局
 * 子View可点击
 * @author yancheng
 * @since 2021/12/21
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //开启吸附功能
        vDragFloatingView.openAdsorption()
        vIv1.setOnClickListener(this)
        vIv2.setOnClickListener(this)
        vStartFloatView.setOnClickListener(this)
        vEndFloatView.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v){
            vIv1 -> Toast.makeText(this, "麦克风", Toast.LENGTH_SHORT).show()
            vIv2 -> Toast.makeText(this, "头像", Toast.LENGTH_SHORT).show()
            vStartFloatView -> startWindowFloating()
            vEndFloatView -> endWindowFloating()
        }
    }

    /**
     * 打开浮动到Window的Button
     */
   private fun startWindowFloating() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "请授权", Toast.LENGTH_SHORT)
            startActivityForResult(
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                ), 0
            )
        } else {
            if (FloatingWindowButton.isStarted) {
                return
            }
            startService(Intent(this@MainActivity, FloatingWindowButton::class.java))
        }
        FloatingWindowButton
    }

    private fun endWindowFloating() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show()
                startService(Intent(this@MainActivity, FloatingWindowButton::class.java))
            }
        }
//        } else if (requestCode == 1) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
//                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show()
//                startService(Intent(this@MainActivity, FloatingImageDisplayService::class.java))
//            }
//        } else if (requestCode == 2) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
//                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show()
//                startService(Intent(this@MainActivity, FloatingVideoService::class.java))
//            }
//        }
    }




}