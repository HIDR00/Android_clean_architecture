package com.aliasadi.clean.ui.onboard

import AppTextStyle
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aliasadi.clean.R
import com.aliasadi.clean.ui.feed.FeedNavigationState.MovieDetails
import com.aliasadi.clean.ui.main.MainRouter
import com.aliasadi.clean.ui.theme.AppColor
import com.aliasadi.clean.util.collectAsEffect
import com.aliasadi.clean.ui.onboard.OnBoardNavigationState.NavigationBar

/**
 * @author by Ali Asadi on 15/04/2023
 */

@Composable
fun OnBoardPage(
    mainRouter: MainRouter,
    viewModel: OnBoardViewModel,
) {
    viewModel.navigationState.collectAsEffect { navigationState ->
        when (navigationState) {
            is NavigationBar -> mainRouter.navigateToNavigationBar()
        }
    }

    val state by viewModel.uiState.collectAsState()
    OnBoardScreen(state,
        onNextPageClick = { viewModel.onNextPageClick(state.index + 1) },
        onPreviousPageClick = { viewModel.onNextPageClick(state.index - 1) },
//        mainNavController,
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardScreen(
    state: OnBoardState,
    onNextPageClick: () -> Unit,
    onPreviousPageClick: () -> Unit
//    appNavController: NavHostController
) {




    Scaffold(
        containerColor = AppColor.White,
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(state.imageList[state.index]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(294.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            PageIndicator(state.imageList.size,state.index)
            Spacer(modifier = Modifier.height(40.dp))
            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                Text(text = state.titleList[state.index],
                    color = AppColor.Blue900,
                    style = AppTextStyle.title1.semiBold()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = state.contentList[state.index],
                    style = AppTextStyle.body2.regular(),
                    color = AppColor.Neutral500
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if(state.index == 0) Box(modifier = Modifier.size(56.dp)) else ButtonPageChange(onClick = onPreviousPageClick, isBack = true)
                    ButtonPageChange(onClick = onNextPageClick, isBack = false)
                }
            }
        }
    }
}

@Composable
private fun PageIndicator(pageCount: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        repeat(pageCount){
            IndicatorSingleDot(isSelected = it == currentPage)
        }
    }
}

@Composable
private fun IndicatorSingleDot(isSelected: Boolean) {
    val width = animateDpAsState(targetValue = if(isSelected) 24.dp else 8.dp, label = "")
    Box(
        modifier = Modifier
            .padding(2.dp)
            .height(8.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(if (isSelected) AppColor.Orange500 else AppColor.Neutral200)
    ){}
}

@Composable
private fun ButtonPageChange(onClick: () -> Unit,isBack: Boolean) {
    Button(onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if(isBack) AppColor.White else AppColor.Orange500,
            contentColor = if(isBack) AppColor.Orange500 else AppColor.White
        ),
        border = BorderStroke(color = AppColor.Orange500, width = 1.dp),
        modifier = Modifier.size(56.dp),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
        ) {
            Icon(painter = painterResource(id = if(isBack) R.drawable.ic_arrow_left else R.drawable.ic_arrow_right), contentDescription = "")
    }
}

//@Preview(name = "Light")
//@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun MovieDetailsScreenPreview() {
//    PreviewContainer {
//        MovieDetailsScreen(
//            MovieDetailsState(
//                imageUrl = "https://i.stack.imgur.com/lDFzt.jpg",
//                title = "Avatar",
//                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore.",
//                isFavorite = false,
//            ),
//            onFavoriteClick = {},
//            rememberNavController()
//        )
//    }
//}
