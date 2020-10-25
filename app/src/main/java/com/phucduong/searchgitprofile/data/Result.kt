/*
 * Copyright (C) 2019 The Android Open Source Project
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

package com.phucduong.searchgitprofile.data

import com.phucduong.searchgitprofile.data.remote.model.ErrorResponse
import java.io.IOException

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorResponse: ErrorResponse?) : Result<Nothing>()
    data class NetWorkError(val exception: IOException) : Result<Nothing>()
    data class UnKnowError(val throwable: Throwable) : Result<Nothing>()
}
