/*
 * This file is part of the UNES Open Source Project.
 * UNES is licensed under the GNU GPLv3.
 *
 * Copyright (c) 2020. João Paulo Sena <joaopaulo761@gmail.com>
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

package com.forcetower.uefs.core.model.siecomp

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.UUID

@Entity(
    indices = [
        Index(value = ["uuid"], unique = true)
    ]
)
data class Tag(
    @SerializedName(value = "uid", alternate = ["id"])
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0,
    val name: String,
    val color: Int,
    val internal: Boolean = false,
    @SerializedName("font_color")
    val fontColor: Int? = null,
    val uuid: String = UUID.randomUUID().toString()
) {
    override fun toString(): String {
        return name
    }
}
