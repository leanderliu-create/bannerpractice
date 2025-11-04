package com.tpi.banner_practice.banner.repository

import android.content.Context
import com.google.gson.Gson
import com.tpi.banner_practice.banner.model.BannerResponse
import com.tpi.banner_practice.banner.model.DraftData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

/**
 * Banner 數據倉庫
 * 負責從本地或遠程數據源獲取數據
 */
class BannerRepository(private val context: Context) {

    private val gson = Gson()

    /**
     * 從本地 assets 文件加載 Banner 數據
     */
    suspend fun loadBannerData(): Result<BannerResponse> = withContext(Dispatchers.IO) {
        try {
            val inputStream = context.assets.open("mock_data.json")
            val reader = InputStreamReader(inputStream, "UTF-8")
            val bannerResponse = gson.fromJson(reader, BannerResponse::class.java)
            reader.close()
            Result.success(bannerResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 根據 TagNo 獲取對應的 DraftData 列表
     */
    suspend fun getDraftDataByTag(tagNo: String): Result<List<DraftData>> {
        return loadBannerData().map { response ->
            response.draftData.filter { it.tagNo == tagNo }
                .sortedBy { it.draftSort }
        }
    }
}