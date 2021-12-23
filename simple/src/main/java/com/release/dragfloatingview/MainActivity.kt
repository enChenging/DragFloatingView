package com.release.dragfloatingview

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.release.draglibrary.DragFloatingView

/**
 * 自定义可拖拽布局
 * 子View可点击
 * @author yancheng
 * @since 2021/12/21
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //开启吸附功能
        findViewById<DragFloatingView>(R.id.vDragFloatingView).openAdsorption()

        findViewById<ImageView>(R.id.vIv1).setOnClickListener {
            Toast.makeText(this, "麦克风", Toast.LENGTH_SHORT).show()
        }
        findViewById<ImageView>(R.id.vIv2).setOnClickListener {
            Toast.makeText(this, "头像", Toast.LENGTH_SHORT).show()
        }
    }

}