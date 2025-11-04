package com.tpi.banner_practice.base

import androidx.appcompat.app.AppCompatDialogFragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager

abstract class BaseDialogFragment : AppCompatDialogFragment(){
    override fun onStart() {
        super.onStart()

        dialog?.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
            setLayout(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        // Avoids to add same fragment or DialogFragment twice before dismissing.
        if (isAdded) return
        manager.findFragmentByTag(tag) ?: super.show(manager, tag)
    }

    override fun dismissAllowingStateLoss() {
        if (!isAdded) return
        super.dismissAllowingStateLoss()
    }
}