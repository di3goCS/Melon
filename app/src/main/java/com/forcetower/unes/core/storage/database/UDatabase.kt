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

package com.forcetower.unes.core.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.forcetower.unes.core.model.*
import com.forcetower.unes.core.storage.database.dao.*

@Database(entities = [
    Access::class,
    Profile::class,
    Semester::class,
    Message::class,
    CalendarItem::class,
    Discipline::class,
    AccessToken::class
], version = 1, exportSchema = true)
abstract class UDatabase: RoomDatabase() {
    abstract fun accessDao(): AccessDao
    abstract fun accessTokenDao(): AccessTokenDao
    abstract fun profileDao(): ProfileDao
    abstract fun messageDao(): MessageDao
    abstract fun semesterDao(): SemesterDao
    abstract fun calendarDao(): CalendarDao
    abstract fun disciplineDao(): DisciplineDao
}