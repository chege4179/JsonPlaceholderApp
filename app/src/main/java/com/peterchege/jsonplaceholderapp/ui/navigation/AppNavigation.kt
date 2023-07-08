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
package com.peterchege.jsonplaceholderapp.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.peterchege.jsonplaceholderapp.core.util.Screens
import com.peterchege.jsonplaceholderapp.ui.screens.all_posts.AllPostsScreen
import com.peterchege.jsonplaceholderapp.ui.screens.post.PostScreen


@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.ALL_POSTS_SCREEN
    ) {

        composable(route = Screens.ALL_POSTS_SCREEN) {
            AllPostsScreen(navController = navController)
        }
        composable(
            route = Screens.POST_SCREEN + "/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) {
            val postId = it.arguments?.getString("postId")
                ?: throw IllegalStateException("Post Id missing.")
            PostScreen(navController = navController,postId = postId)
        }
    }
}