package com.tpi.banner_practice.banner.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.tpi.banner_practice.banner.adapter.BannerAdapter
import com.tpi.banner_practice.banner.viewmodel.BannerViewModel
import com.tpi.banner_practice.base.BaseFragment
import com.tpi.banner_practice.databinding.FragmentBannerBinding

class BannerFragment : BaseFragment() {
    private var _binding: FragmentBannerBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: BannerViewModel by viewModels()
    private var autoScrollHandler: Handler? = null
    private var autoScrollRunnable: Runnable? = null
    private val autoScrollDelay = 5000L // 3秒自動切換
    private lateinit var bannerAdapter: BannerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBannerBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBannerAdapter()
        setupObservers()

        getBannerData()//取輪播data
    }

    /**
     * 設置 LiveData 觀察者
     */
    private fun setupObservers() {
        // 觀察 Banner標題
        viewModel.bannerTitle.observe(viewLifecycleOwner) { bannerTitle ->
            binding.tTitle.text = bannerTitle
        }

        // 觀察輪播數據列表
        viewModel.draftList.observe(viewLifecycleOwner) { draftList ->
            draftList?.let {
                bannerAdapter.submitList(it)
                binding.ilIndicatorLayout.setupIndicators(count = it.size, defaultSelected = 0)
            }
        }

        // 觀察錯誤信息
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                // 顯示錯誤提示
                // TODO: 可以根據需求顯示 Toast 或 Snackbar
                viewModel.clearError()
            }
        }
    }

    /**取得輪播Data*/
    private fun getBannerData() {
        viewModel.loadBannerData()
    }
    /**
     * 設置 Banner Adapter
     */
    private fun setupBannerAdapter() {
        bannerAdapter = BannerAdapter { draftData ->
            // 點擊 Banner 項目的處理
            Toast.makeText(requireContext(), "點擊了: ${draftData.draftTitle}", Toast.LENGTH_SHORT).show()
        }

        binding.viewPagerBanner.adapter = bannerAdapter

        // 設置 ViewPager2 頁面變化監聽器
        binding.viewPagerBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.ilIndicatorLayout.setSelectedPosition(position)
                // 當用戶手動滑動時，重新啟動自動輪播
                startAutoScroll()
            }
        })
    }
    /**
     * 開始自動輪播
     */
    private fun startAutoScroll() {
        stopAutoScroll()

        autoScrollHandler = Handler(Looper.getMainLooper())
        autoScrollRunnable = Runnable {
            val currentItem = binding.viewPagerBanner.currentItem
            val itemCount = bannerAdapter.itemCount
            if (itemCount > 0) {
                val nextItem = (currentItem + 1) % itemCount
                binding.viewPagerBanner.setCurrentItem(nextItem, true)
                startAutoScroll()
            }
        }

        autoScrollHandler?.postDelayed(autoScrollRunnable!!, autoScrollDelay)
    }

    /**
     * 停止自動輪播
     */
    private fun stopAutoScroll() {
        autoScrollRunnable?.let { autoScrollHandler?.removeCallbacks(it) }
        autoScrollRunnable = null
        autoScrollHandler = null
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}