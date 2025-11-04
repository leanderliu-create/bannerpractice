package com.tpi.banner_practice.banner.usecase

import com.tpi.banner_practice.banner.model.BannerResponse
import com.tpi.banner_practice.banner.repository.BannerRepository

/**
 * 獲取 Banner 數據的 UseCase
 * 封裝獲取 Banner 數據的業務邏輯
 */
class GetBannerDataUseCase(
    private val repository: BannerRepository
) {
    /**
     * 執行獲取 Banner 數據的操作
     */
    suspend fun execute(): Result<BannerResponse> {
        return repository.loadBannerData()
    }
}

