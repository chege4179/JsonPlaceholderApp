/*
 * Copyright 2023 Json Placeholder App by Peter Chege
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peterchege.jsonplaceholderapp.ui.screens.all_posts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.jsonplaceholderapp.core.util.Screens
import com.peterchege.jsonplaceholderapp.domain.AllPostScreenUiState
import com.peterchege.jsonplaceholderapp.ui.components.ErrorComponent
import com.peterchege.jsonplaceholderapp.ui.components.LoadingComponent
import com.peterchege.jsonplaceholderapp.ui.components.PostCard
import com.peterchege.jsonplaceholderapp.ui.screens.destinations.PostScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun AllPostsScreen(
    navigator: DestinationsNavigator

) {
    val viewModel = getViewModel<AllPostsScreenViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AllPostsScreenContent(
        uiState = uiState,
        retryCallBack = { viewModel.getAllPosts() },
        onPostClick = { navigator.navigate(PostScreenDestination(it)) }
    )

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllPostsScreenContent(
    uiState: AllPostScreenUiState,
    retryCallBack: () -> Unit,
    onPostClick: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Posts")
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            when (uiState) {
                is AllPostScreenUiState.Loading -> {
                    LoadingComponent()
                }

                is AllPostScreenUiState.Error -> {
                    ErrorComponent(
                        retryCallback = { retryCallBack() },
                        errorMessage = uiState.message
                    )
                }

                is AllPostScreenUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(items = uiState.data.posts, key = { it.id }) { post ->
                            PostCard(
                                post = post,
                                onPostClick = {
                                    onPostClick(it.id.toString())
                                }
                            )
                            Spacer(modifier = Modifier.height(5.dp))

                        }

                    }
                }
            }
        }
    }


}