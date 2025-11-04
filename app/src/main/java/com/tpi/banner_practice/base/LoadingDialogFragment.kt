package com.tpi.banner_practice.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.tpi.banner_practice.databinding.DialogfragmentLoadingBinding

class LoadingDialogFragment : BaseDialogFragment() {

    private var _binding: DialogfragmentLoadingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogfragmentLoadingBinding.inflate(layoutInflater, container, false)

        initLoadingGif()

        return _binding?.root
    }

    @SuppressLint("WrongConstant")
    private fun initLoadingGif() {
        binding.progressBar.apply {
//            imageAssetsFolder = "images"
//            setAnimation("")
//            repeatMode = LottieDrawable.INFINITE
//            repeatCount = ValueAnimator.INFINITE
//            playAnimation()
        }
    }

    override fun onDestroyView() {
        binding.progressBar.apply {
            clearAnimation()
        }
        super.onDestroyView()
        _binding = null
        instance = null
    }

    companion object {
        private const val TAG = "LOADING"

        @Volatile
        private var instance: LoadingDialogFragment? = null

        // 不要再改啦>< 這樣就好啦!
        //20231003:還是報錯not associated with a fragment manager.Q_Q
        fun show(fragmentManager: FragmentManager) {
            synchronized(this) {
                if (instance?.isVisible == true || instance?.isAdded == true) return
                instance?.dismiss()
                if (instance == null) {
                    instance = LoadingDialogFragment()
                }
                instance?.show(fragmentManager, TAG)
            }
        }

        fun hide() {
            synchronized(this) {
                instance?.dismissAllowingStateLoss()
                instance = null
            }
        }
    }
}