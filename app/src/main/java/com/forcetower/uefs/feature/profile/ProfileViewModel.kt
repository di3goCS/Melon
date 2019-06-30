/*
 * Copyright (c) 2019.
 * João Paulo Sena <joaopaulo761@gmail.com>
 *
 * This file is part of the UNES Open Source Project.
 *
 * UNES is licensed under the MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.forcetower.uefs.feature.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.forcetower.uefs.core.model.unes.SStudent
import com.forcetower.uefs.core.storage.repository.ProfileRepository
import com.forcetower.uefs.feature.shared.extensions.setValueIfNew
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {
    private val profileId = MutableLiveData<Long?>()

    private val _profile = MediatorLiveData<SStudent?>()
    val profile: LiveData<SStudent?>
        get() = _profile

    val accountDatabase = repository.getAccountDatabase()

    init {
        _profile.addSource(profileId) {
            refreshProfile(it)
        }
    }

    private fun refreshProfile(profileId: Long?) {
        if (profileId != null) {
            val source = repository.loadProfile(profileId)
            Timber.d("Fetching profile...")
            _profile.addSource(source) {
                Timber.d("Profile load update ${it.status}")
                val data = it.data
                if (data != null) {
                    _profile.value = data
                }
            }
        } else {
            Timber.d("No profile information available")
        }
    }

    fun getMeProfile() = repository.getMeProfile()

    fun setProfileId(newProfileId: Long?) {
        Timber.d("Setting new profile id: $newProfileId")
        profileId.setValueIfNew(newProfileId)
    }
}