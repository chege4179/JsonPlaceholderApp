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
package com.peterchege.jsonplaceholderapp.ui.screens.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.jsonplaceholderapp.domain.AllPostScreenUiState
import com.peterchege.jsonplaceholderapp.domain.PostScreenUiState
import com.peterchege.jsonplaceholderapp.ui.components.ErrorComponent
import com.peterchege.jsonplaceholderapp.ui.components.LoadingComponent
import com.peterchege.jsonplaceholderapp.ui.components.PostCard
import org.koin.androidx.compose.getViewModel

@Composable
fun PostScreen(
    navController: NavController,
    postId:String,
) {
    val viewModel = getViewModel<PostScreenViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PostScreenContent(
        uiState = uiState,
        retryCallback = { viewModel.getPostById(postId = postId) }
    )


}

@Composable
fun PostScreenContent(
    uiState:PostScreenUiState,
    retryCallback:() -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        when (uiState) {
            is PostScreenUiState.Loading -> {
                LoadingComponent()
            }

            is PostScreenUiState.Error -> {
                ErrorComponent(
                    retryCallback = { retryCallback() },
                    errorMessage = uiState.message
                )
            }

            is PostScreenUiState.Success -> {
                Text(text = "Id : ${uiState.data.post.id}")
                Text(text = "Title : ${uiState.data.post.title}")
                Text(text = "Body : ${uiState.data.post.body}")
                Text(text = "User ID : ${uiState.data.post.userId}")
            }
        }
    }


}

