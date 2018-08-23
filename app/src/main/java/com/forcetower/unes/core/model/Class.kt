/*
 * Copyright (c) 2018.
 *
 * This file is part of the UNES Open Source Project.
 *
 * UNES is licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.forcetower.unes.core.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(entity = Discipline::class, parentColumns = ["uid"], childColumns = ["discipline_id"], onDelete = CASCADE, onUpdate = CASCADE),
        ForeignKey(entity = Semester::class, parentColumns = ["uid"], childColumns = ["semester_id"], onDelete = CASCADE, onUpdate = CASCADE)
    ], indices = [
        Index(value = ["discipline_id", "semester_id", "code"], unique = true),
        Index(value = ["uuid"], unique = true)
    ]
)
data class Class(
    @PrimaryKey(autoGenerate = true)
    val uid: Long,
    @ColumnInfo(name = "discipline_id")
    val disciplineId: Long,
    @ColumnInfo(name = "semester_id")
    val semesterId: Long,
    val code: String,
    val teacher: String? = null,
    val status: String? = null,
    val uuid: String = UUID.randomUUID().toString(),
    val credits: Int? = null
)