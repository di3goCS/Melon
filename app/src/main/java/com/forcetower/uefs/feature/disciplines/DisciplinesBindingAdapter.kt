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

package com.forcetower.uefs.feature.disciplines

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.forcetower.uefs.R
import com.forcetower.uefs.core.model.unes.Grade
import com.forcetower.uefs.core.storage.database.accessors.ClassWithGroups
import com.forcetower.uefs.core.util.round
import com.forcetower.uefs.feature.common.DisciplineActions
import com.forcetower.uefs.feature.grades.ClassGroupGradesAdapter
import com.forcetower.uefs.widget.CircleProgressBar
import timber.log.Timber

@BindingAdapter(value = ["disciplineGroupsGrades", "disciplineListener"])
fun disciplineGroupsGrades(recycler: RecyclerView, classes: List<Grade>?, listener: DisciplineActions?) {
    val sort = classes?.sortedWith(Comparator { one, two ->
        when {
            one.name.trim().equals("prova final", ignoreCase = true) -> 1
            two.name.trim().equals("prova final", ignoreCase = true) -> -1
            else -> one.name.compareTo(two.name)
        }
    })

    val adapter: ClassGroupGradesAdapter
    if (recycler.adapter == null) {
        adapter = ClassGroupGradesAdapter(listener)
        recycler.adapter = adapter
    } else {
        adapter = recycler.adapter as ClassGroupGradesAdapter
    }

    adapter.submitList(sort)
}

@BindingAdapter("classStudentGrade")
fun classStudentGrade(cpb: CircleProgressBar, clazz: ClassWithGroups?) {
    val value = clazz?.clazz?.finalScore
    if (value == null) {
        cpb.setProgress(0.0f)
    } else {
        cpb.setProgressWithAnimation(value.toFloat() * 10)
    }
}

@BindingAdapter("classStudentGrade")
fun classStudentGrade(tv: TextView, clazz: ClassWithGroups?) {
    val value = clazz?.clazz?.finalScore
    if (value == null) {
        tv.text = "??"
    } else {
        tv.text = value.toString()
    }
}

@BindingAdapter("gradeNeededInFinal")
fun gradeNeededInFinal(tv: TextView, clazz: ClassWithGroups?) {
    val value = clazz?.clazz?.partialScore
    if (value == null) {
        tv.text = "??"
    } else {
        val needed = (12.5 - (1.5 * value)).round()
        tv.text = tv.context.getString(R.string.grade_format, needed)
    }
}

fun getClassWithGroupsGrade(clazz: ClassWithGroups): Double? {
    if (clazz.groups.isNotEmpty()) {
        return clazz.clazz.finalScore
    }
    return null
}

@BindingAdapter(value = ["missedDescription", "missedDate"], requireAll = true)
fun classAbsence(tv: TextView, desc: String?, date: String?) {
    tv.text = tv.context.getString(R.string.discipline_absence_item_format, desc ?: tv.context.getString(R.string.not_registed), date ?: tv.context.getString(R.string.not_registed))
}

@BindingAdapter(value = ["absences", "credits"], requireAll = true)
fun totalAbsence(tv: TextView, absences: Int?, credits: Int?) {
    val context = tv.context
    if (absences == null || credits == null || credits == 0) {
        tv.text = context.getString(R.string.discipline_credits_undefined)
    } else {
        Timber.d("Credits: $credits __ Absence: $absences")
        val left = (credits / 4) - absences
        when {
            left > 0 -> tv.text = context.getString(R.string.discipline_absence_left, left)
            left == 0 -> tv.text = context.getString(R.string.you_cant_miss_a_class)
            else -> tv.text = context.getString(R.string.you_missed_to_many_classes)
        }
    }
}

@BindingAdapter(value = ["disciplineCredits"])
fun credits(tv: TextView, credits: Int?) {
    tv.text = credits?.toString()?.plus("h") ?: "??h"
}

@BindingAdapter(value = ["somethingOrQuestions"])
fun somethingOrQuestions(tv: TextView, something: String?) {
    val text = something ?: "????"
    tv.text = text
}

@BindingAdapter(value = ["classSubject", "classSituation"], requireAll = true)
fun classSubject(tv: TextView, subject: String?, situation: String?) {
    val text = subject ?: "????"
    tv.text = text

    val strike = situation?.trim()?.equals("realizada", ignoreCase = true)

    if (strike == true) tv.paintFlags = tv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    else tv.paintFlags = tv.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}

@BindingAdapter(value = ["absenceSequence", "absenceDate"], requireAll = true)
fun disciplineAbsence(tv: TextView, sequence: Int?, date: String?) {
    val ctx = tv.context
    val seq = sequence ?: 0
    val dat = date ?: "??/??/????"

    val text = ctx.getString(R.string.discipline_absence_date_format, seq, dat)
    tv.text = text
}

@BindingAdapter(value = ["absenceDescription"])
fun absenceDescription(tv: TextView, description: String?) {
    val desc = description ?: "CL 2 - ????"
    val text = desc.substring(desc.indexOf("-") + 1).trim()
    tv.text = text
}