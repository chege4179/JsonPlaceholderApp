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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.jsonplaceholderapp.core.util.NetworkResult
import com.peterchege.jsonplaceholderapp.data.PostRepository
import com.peterchege.jsonplaceholderapp.domain.PostScreenUi
import com.peterchege.jsonplaceholderapp.domain.PostScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostScreenViewModel (
    val repository:PostRepository,
    val savedStateHandle: SavedStateHandle
):ViewModel() {

    private val _uiState = MutableStateFlow<PostScreenUiState>(PostScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        savedStateHandle.get<String>("postId")?.let {
            getPostById(postId = it)
        }
    }


    fun getPostById(postId:String){
        viewModelScope.launch {
            val response = repository.getPostById(postId = postId)
            when (response){
                is NetworkResult.Success -> {
                    _uiState.value = PostScreenUiState.Success(data = PostScreenUi(post = response.data))

                }
                is NetworkResult.Error -> {
                    _uiState.value = PostScreenUiState.Error(message = response.message ?:" An unexpected error occurred")
                }
                is NetworkResult.Exception -> {
                    _uiState.value = PostScreenUiState.Error(message = response.e.message ?: "An unexpected exception occurred")

                }
            }
        }
    }
}