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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.jsonplaceholderapp.core.util.NetworkResult
import com.peterchege.jsonplaceholderapp.data.PostRepository
import com.peterchege.jsonplaceholderapp.domain.AllPostScreenUi
import com.peterchege.jsonplaceholderapp.domain.AllPostScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class AllPostsScreenViewModel(
    val repository: PostRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<AllPostScreenUiState>(AllPostScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getAllPosts()
    }

    fun getAllPosts(){
        viewModelScope.launch {
            val response = repository.getAllPosts()
            when (response){
                is NetworkResult.Success -> {
                    _uiState.value = AllPostScreenUiState.Success(
                        data = AllPostScreenUi(posts = response.data))
                }
                is NetworkResult.Error -> {
                    _uiState.value = AllPostScreenUiState.Error(message = response.message ?: "An unexpected error occurred")
                }
                is NetworkResult.Exception -> {
                    _uiState.value = AllPostScreenUiState.Error(message = response.e.message ?: "An unexpected exception occurred")
                }
            }
        }
    }
}