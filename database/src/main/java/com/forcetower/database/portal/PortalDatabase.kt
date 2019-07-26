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

package com.forcetower.database.portal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.forcetower.database.portal.dao.CredentialDao
import com.forcetower.model.portal.Credential

@Database(entities = [
    Credential::class
], version = 1)
abstract class PortalDatabase : RoomDatabase() {
    abstract fun credentials(): CredentialDao

    companion object {
        private const val DATABASE_NAME = "portal.db"
        @Volatile private var instance: PortalDatabase? = null

        fun getInstance(context: Context): PortalDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): PortalDatabase {
            return Room.databaseBuilder(context, PortalDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}