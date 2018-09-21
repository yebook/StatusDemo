package com.huiming.statusdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.gyf.barlibrary.ImmersionBar
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropTransformation
import kotlinx.android.synthetic.main.activity_pictitle.*
import com.bumptech.glide.request.RequestOptions


/**
 * Created by kermitye
 * Date: 2018/9/21 9:39
 * Desc:
 */
class PicTitleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pictitle)
        ImmersionBar.with(this).statusBarView(mTopView).fullScreen(true).init()
        initData()
        initHeadPic()
    }

    fun initData() {
        var text = StringBuffer("测试数据\n")
        for (i in 0 ..50) {
            text.append("测试数据$i \n")
        }
        mTv.text = text
    }

    fun initHeadPic() {
        //高斯模糊处理
        Glide.with(this)
                .load(R.mipmap.test)
                .apply(bitmapTransform(BlurTransformation(14, 3)))
                .into(mIvHeadView)
        Glide.with(this)
                .load(R.mipmap.test)
                .apply(bitmapTransform(BlurTransformation(14, 3)))
                .into(mIvBarView)


        //将标题图片上移至状态栏+标题栏高度的底部部位
        val params = mIvBarView.getLayoutParams()
        //状态栏+标题栏的高度
        var topHeight = mToolBar.layoutParams.height + getStatusHeight()

        val ivTitleHeadBgParams = mIvBarView.getLayoutParams() as ViewGroup.MarginLayoutParams
        val marginTop = params.height - topHeight
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0)

        //监听滑动状态设置透明度
        mScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            var headHight = mIvHeadView.layoutParams.height
            var flagHight = headHight - topHeight
            var alpha = 0f
            var scrolledY = if (scrollY < 0) 0 else scrollY

            if (flagHight > 0) {
                if (scrolledY < flagHight) {
                    alpha = scrolledY.toFloat() / (flagHight + 50)
                } else {
                    alpha = 1f
                }
            }
            mIvBarView.alpha = alpha
        }
    }

}