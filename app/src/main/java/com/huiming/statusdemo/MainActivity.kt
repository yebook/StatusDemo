package com.huiming.statusdemo

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImmersionBar.with(this).titleBar(mToolBar).init()
        var text = StringBuffer("测试数据\n")
        for (i in 0 ..50) {
            text.append("测试数据$i \n")
        }
        mTv.text = text
        mScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            var headHight = mIvHead.layoutParams.height
            var titleHight = mToolBar.layoutParams.height + getStatusHeight()
            var flagHight = headHight - titleHight
            var alpha = 0
            var scrolledY = if (scrollY < 0) 0 else scrollY

            if (flagHight > 0) {
                if (scrolledY < flagHight) {
                    alpha = scrolledY * 100 / flagHight
                } else {
                    alpha = 99
                }
            }
            var colorValue = "#${if (alpha < 10) "0" + alpha else alpha}00b0ff"
            mToolBar.setBackgroundColor(Color.parseColor(colorValue))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.with(this).destroy()
    }

    fun jump(v: View) {
        startActivity<PicTitleActivity>()
    }
}
