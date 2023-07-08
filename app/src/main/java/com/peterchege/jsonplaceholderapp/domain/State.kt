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
package com.peterchege.jsonplaceholderapp.domain

import com.peterchege.jsonplaceholderapp.core.api.responses.Post
import com.skydoves.sealedx.core.Extensive
import com.skydoves.sealedx.core.annotations.ExtensiveModel
import com.skydoves.sealedx.core.annotations.ExtensiveSealed

@ExtensiveSealed(
    models = [
        ExtensiveModel(AllPostScreenUi::class),
        ExtensiveModel(PostScreenUi::class),
    ]
)
sealed interface State {
    data class Success(val data: Extensive) : State
    object Loading : State
    data class Error(val message:String) : State
}

data class AllPostScreenUi(
    val posts:List<Post>
)
data class PostScreenUi(
    val post:Post,

)