package com.tpi.banner_practice.banner.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tpi.banner_practice.banner.model.DraftData
import com.tpi.banner_practice.databinding.ItemBannerBinding

/**
 * Banner 輪播適配器
 */
class BannerAdapter(
    private val onItemClick: (DraftData) -> Unit = {}
) : ListAdapter<DraftData, BannerAdapter.BannerViewHolder>(BannerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ItemBannerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BannerViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BannerViewHolder(
        private val binding: ItemBannerBinding,
        private val onItemClick: (DraftData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(draftData: DraftData) {
            // 使用 Coil 加載圖片
            binding.imageViewBanner.load(draftData.draftPic) {
                placeholder(android.R.drawable.ic_menu_gallery)
                error(android.R.drawable.ic_menu_report_image)
            }

            binding.root.setOnClickListener {
                onItemClick(draftData)
            }
        }
    }

    private class BannerDiffCallback : DiffUtil.ItemCallback<DraftData>() {
        override fun areItemsTheSame(oldItem: DraftData, newItem: DraftData): Boolean {
            return oldItem.draftTitle == newItem.draftTitle
        }

        override fun areContentsTheSame(oldItem: DraftData, newItem: DraftData): Boolean {
            return oldItem == newItem
        }
    }
}

