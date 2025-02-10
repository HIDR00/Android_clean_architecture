package com.aliasadi.clean.ui.onboard

data class OnBoardState (
    val index: Int = 0,
    val imageList: List<Int> = emptyList(),
    val titleList: List<String> = emptyList(),
    val contentList: List<String> = emptyList(),
)

sealed class OnBoardNavigationState {
    data object NavigationBar : OnBoardNavigationState()
}