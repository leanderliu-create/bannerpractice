package com.tpi.banner_practice.base.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.tpi.banner_practice.R
import com.tpi.banner_practice.base.ui.utils.ViewAnimationUtils

/**
 * 可動畫化的指示器容器，用於 ViewPager2 或 Banner。
 * 呼叫 [setSelectedPosition] 以更新當前選中項目。
 */
class IndicatorLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var indicatorCount = 0
    private var selectedPosition = 0

    init {
        orientation = HORIZONTAL
    }

    /**
     * 初始化指示器
     */
    fun setupIndicators(count: Int, defaultSelected: Int = 0) {
        removeAllViews()
        indicatorCount = count
        selectedPosition = defaultSelected

        val margin = resources.getDimensionPixelSize(R.dimen.indicator_margin)
        val size = resources.getDimensionPixelSize(R.dimen.indicator_size)
        val selectedWidth = resources.getDimensionPixelSize(R.dimen.indicator_selected_width)
        val selectedHeight = resources.getDimensionPixelSize(R.dimen.indicator_selected_height)

        for (i in 0 until count) {
            val indicator = View(context).apply {
                background = ContextCompat.getDrawable(
                    context,
                    if (i == selectedPosition) R.drawable.indicator_selected else R.drawable.indicator_unselected
                )
            }

            val params = LayoutParams(
                if (i == selectedPosition) selectedWidth else size,
                if (i == selectedPosition) selectedHeight else size
            )
            params.setMargins(margin, 0, margin, 0)
            addView(indicator, params)
        }
    }

    /**
     * 更新指示器顯示（帶動畫）
     */
    fun setSelectedPosition(position: Int) {
        if (position == selectedPosition || position < 0 || position >= indicatorCount) return
        updateIndicators(position)
        selectedPosition = position
    }

    /**
     * 執行動畫更新
     */
    private fun updateIndicators(position: Int) {
        val childCount = childCount
        val selectedWidth = resources.getDimensionPixelSize(R.dimen.indicator_selected_width)
        val selectedHeight = resources.getDimensionPixelSize(R.dimen.indicator_selected_height)
        val unselectedSize = resources.getDimensionPixelSize(R.dimen.indicator_size)
        val margin = resources.getDimensionPixelSize(R.dimen.indicator_margin)

        for (i in 0 until childCount) {
            val indicator = getChildAt(i)
            val isSelected = i == position
            val params = indicator.layoutParams as LayoutParams

            // 更新背景
            indicator.setBackgroundResource(
                if (isSelected) R.drawable.indicator_selected else R.drawable.indicator_unselected
            )

            val currentWidth = params.width
            val currentHeight = params.height
            val targetWidth = if (isSelected) selectedWidth else unselectedSize
            val targetHeight = if (isSelected) selectedHeight else unselectedSize

            if (currentWidth != targetWidth || currentHeight != targetHeight) {
                ViewAnimationUtils.animateIndicatorSize(
                    indicator = indicator,
                    params = params,
                    startWidth = currentWidth,
                    startHeight = currentHeight,
                    endWidth = targetWidth,
                    endHeight = targetHeight,
                    margin = margin,
                    overshoot = true
                )
            } else {
                params.width = targetWidth
                params.height = targetHeight
                params.setMargins(margin, 0, margin, 0)
                indicator.layoutParams = params
            }
        }
    }
}