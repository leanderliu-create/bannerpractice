package com.tpi.banner_practice.banner.model

import com.google.gson.annotations.SerializedName

/**
 * Banner 數據響應模型
 */
data class BannerResponse(
    @SerializedName("HeadTitle")
    val headTitle: String,
    @SerializedName("TagData")
    val tagData: List<TagData>,
    @SerializedName("DraftData")
    val draftData: List<DraftData>
)

/**
 * 標籤數據模型
 */
data class TagData(
    @SerializedName("TagNo")
    val tagNo: String,
    @SerializedName("TagTitle")
    val tagTitle: String,
    @SerializedName("TagSort")
    val tagSort: Int
)

/**
 * 輪播草稿數據模型
 */
data class DraftData(
    @SerializedName("TagNo")
    val tagNo: String,
    @SerializedName("DraftTitle")
    val draftTitle: String,
    @SerializedName("DraftSubTitle")
    val draftSubTitle: String,
    @SerializedName("DraftPic")
    val draftPic: String,
    @SerializedName("DraftSort")
    val draftSort: Int
)