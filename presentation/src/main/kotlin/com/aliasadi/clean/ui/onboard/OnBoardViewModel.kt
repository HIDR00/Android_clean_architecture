package com.aliasadi.clean.ui.onboard

import android.content.SharedPreferences
import com.aliasadi.clean.R
import com.aliasadi.clean.di.AppSettingsSharedPreference
import com.aliasadi.clean.ui.base.BaseViewModel
import com.aliasadi.clean.ui.main.MainActivity.Companion.IS_FIRST_LAUNCH_APP
import com.aliasadi.clean.ui.onboard.OnBoardState
import com.aliasadi.clean.ui.search.SearchNavigationState
import com.aliasadi.clean.util.singleSharedFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Created by Ali Asadi on 13/05/2020
 */
@HiltViewModel
class OnBoardViewModel @Inject constructor(
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<OnBoardState> = MutableStateFlow(OnBoardState())
    val uiState = _uiState.asStateFlow()

    private val _navigationState: MutableSharedFlow<OnBoardNavigationState> = singleSharedFlow()
    val navigationState = _navigationState.asSharedFlow()

    private val imageList = listOf(
        R.drawable.onboarding_1,
        R.drawable.onboarding_2,
        R.drawable.onboarding_3
    )

    private val titleList = listOf(
        "Quản lý thông tin,\nhồ sơ cá nhân",
        "Cập nhập tin tức,\nsự kiện mới nhất",
        "Dịch vụ thanh toán\nthông minh"
    )

    private val contentList = listOf(
        "Phenikaa Uni cho phép bạn theo dõi, cập nhật thông tin cá nhân, quản lý hồ sơ một cách đơn giản và mọi lúc mọi nơi.",
        "Phenikaa Uni luôn cập nhật tin tức và sự kiện mới nhất, giúp bạn không bỏ lỡ thông tin quan trọng từ Trường.",
        "Phenikaa Uni giúp bạn thanh toán những dịch vụ có trong Trường một cách thuận tiện và nhanh chóng."
    )

    @Inject
    @AppSettingsSharedPreference
    lateinit var appSettings: SharedPreferences

    private fun updateIsFirstLaunchApp() = appSettings.edit().putBoolean(
        IS_FIRST_LAUNCH_APP, false).commit()

    private fun isFirstLaunchApp() = appSettings.getBoolean(IS_FIRST_LAUNCH_APP, true)

    init {
        onInitialState()
    }

    private fun onInitialState() = launch {

        _uiState.value = OnBoardState(
            imageList = imageList,
            contentList = contentList,
            titleList = titleList,
        )
    }

    fun onNextPageClick(value: Int) = launch {
        if(value < 3){
            _uiState.update { it.copy(index = value) }
        }
        else{
            if(isFirstLaunchApp()){
                updateIsFirstLaunchApp()
            }
            _navigationState.tryEmit(OnBoardNavigationState.NavigationBar)
        }
    }

    fun onPreviousPageClick(value: Int) = launch {
        _uiState.update { it.copy(index = value) }
    }
}