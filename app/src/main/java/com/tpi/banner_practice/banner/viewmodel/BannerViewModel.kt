package com.tpi.banner_practice.banner.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tpi.banner_practice.banner.model.DraftData
import com.tpi.banner_practice.banner.model.TagData
import com.tpi.banner_practice.banner.repository.BannerRepository
import com.tpi.banner_practice.banner.usecase.GetBannerDataUseCase
import kotlinx.coroutines.launch

/**
 * Banner ViewModel
 * 負責管理 UI 相關的數據，通過 UseCase 執行業務邏輯
 */
class BannerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BannerRepository(application)

    // UseCase 實例
    private val getBannerDataUseCase = GetBannerDataUseCase(repository)

    // 輪播數據列表
    private val _draftList = MutableLiveData<List<DraftData>>()
    val draftList: LiveData<List<DraftData>> = _draftList

    // 輪播標題
    private val _bannerTitle = MutableLiveData<String>()
    val bannerTitle: LiveData<String> = _bannerTitle

    // 加載狀態
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // 錯誤信息
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    /**
     * 加載 Banner 數據
     */
    fun loadBannerData() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            getBannerDataUseCase.execute().fold(
                onSuccess = { response ->
                    val tagList: List<TagData> = response.tagData
                    val draftList: List<DraftData> = response.draftData

                    val tagNoSet = tagList.map { it.tagNo }.toSet()  // 轉成 Set 提高比對效率
                    val filteredDrafts = draftList.filter { it.tagNo in tagNoSet }

                    _draftList.value = filteredDrafts.sortedBy { it.draftSort }
                    _bannerTitle.value = response.headTitle

                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _errorMessage.value = exception.message ?: "加載數據失敗"
                    _isLoading.value = false
                }
            )
        }
    }

    /**
     * 清除錯誤信息
     */
    fun clearError() {
        _errorMessage.value = null
    }
}