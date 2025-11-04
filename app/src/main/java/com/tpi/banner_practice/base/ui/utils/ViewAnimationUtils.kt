package com.tpi.banner_practice.base.ui.utils
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator

object ViewAnimationUtils {


    /**
     * 動畫化變更 View 的寬與高。
     *
     * @param indicator  要改變尺寸的 View
     * @param params     該 View 的 MarginLayoutParams
     * @param startWidth 開始寬度 (px)
     * @param startHeight 開始高度 (px)
     * @param endWidth   結束寬度 (px)
     * @param endHeight  結束高度 (px)
     * @param margin     四周邊距 (px)
     * @param duration   動畫持續時間 (預設 300ms)
     * @param overshoot  是否使用彈性過渡效果
     */
    fun animateIndicatorSize(
        indicator: View,
        params: ViewGroup.MarginLayoutParams,
        startWidth: Int,
        startHeight: Int,
        endWidth: Int,
        endHeight: Int,
        margin: Int = 0,
        duration: Long = 500,
        overshoot: Boolean = true
    ) {
        // 設定邊距
        params.setMargins(margin, 0, margin, 0)

        // 寬度動畫
        val widthAnimator = ValueAnimator.ofInt(startWidth, endWidth).apply {
            this.duration = duration
            interpolator = if (overshoot) OvershootInterpolator() else DecelerateInterpolator()
            addUpdateListener { animation ->
                params.width = animation.animatedValue as Int
                indicator.requestLayout()
            }
        }

        // 高度動畫
        val heightAnimator = ValueAnimator.ofInt(startHeight, endHeight).apply {
            this.duration = duration
            interpolator = if (overshoot) OvershootInterpolator() else DecelerateInterpolator()
            addUpdateListener { animation ->
                params.height = animation.animatedValue as Int
                indicator.requestLayout()
            }
        }

        // 同步啟動兩個動畫
        AnimatorSet().apply {
            playTogether(widthAnimator, heightAnimator)
            start()
        }
    }

}