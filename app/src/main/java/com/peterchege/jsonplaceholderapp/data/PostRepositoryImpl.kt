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
package com.peterchege.jsonplaceholderapp.data

import com.peterchege.jsonplaceholderapp.core.api.JsonPlaceholderApi
import com.peterchege.jsonplaceholderapp.core.api.responses.AllPostsResponse
import com.peterchege.jsonplaceholderapp.core.api.responses.Post
import com.peterchege.jsonplaceholderapp.core.api.safeApiCall
import com.peterchege.jsonplaceholderapp.core.di.DefaultDispatcherProvider
import com.peterchege.jsonplaceholderapp.core.util.NetworkResult
import kotlinx.coroutines.withContext

class PostRepositoryImpl (
    val defaultDispatcherProvider: DefaultDispatcherProvider,
    val api:JsonPlaceholderApi

):PostRepository {
    override suspend fun getAllPosts(): NetworkResult<List<Post>> {
        return withContext(defaultDispatcherProvider.io){
            safeApiCall { api.getAllPosts() }
        }
    }

    override suspend fun getPostById(postId: String): NetworkResult<Post> {
        return withContext(defaultDispatcherProvider.io){
            safeApiCall { api.getPostById(postId = postId) }
        }
    }
}