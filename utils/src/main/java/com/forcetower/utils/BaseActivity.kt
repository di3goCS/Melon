/*
 * This file is part of the UNES Open Source Project.
 * UNES is licensed under the GNU GPLv3.
 *
 * Copyright (c) 2019. João Paulo Sena <joaopaulo761@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.forcetower.utils

import android.content.pm.ActivityInfo
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.forcetower.utils.extensions.isColorDark
import com.forcetower.utils.extensions.isMarshmellow

abstract class BaseActivity : AppCompatActivity() {
    fun lockOrientation(orientation: Int = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
        requestedOrientation = orientation
    }

    open fun showSnack(string: String, long: Boolean = false) {}

    fun setStatusBarColor(@ColorInt color: Int) {
        if (!isMarshmellow()) return
        val view = window.decorView
        var flags = view.systemUiVisibility
        flags = if (color.isColorDark()) {
            flags and SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        } else {
            flags or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        window.statusBarColor = color
        view.systemUiVisibility = flags
    }
}