package com.example.hotelapp.util

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EmailWatcher(
    private val textInput: TextInputLayout,
    private val scope: LifecycleCoroutineScope
) : TextWatcher {
    private var job: Job = Job()

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        job.cancel()
        job = scope.launch {
            delay(1000)

            // region [REG EXP VALUES]
            val regExWrongFormat = "^[A-Za-z0-9\\-._]+@[A-Za-z0-9\\-_]+\\.[A-Za-z]{2,}$"
                .toRegex(RegexOption.IGNORE_CASE)
            val matchResult = s.matches(regExWrongFormat)

            val regExWrongSymbols = "[^A-Za-z0-9.\\-_@]+"
                .toRegex(RegexOption.IGNORE_CASE)
            val matchWrongSymbols = getWrongSymbols(regExWrongSymbols, s)
            // endregion [REG EXP VALUES]

            // region [VALIDATION]
            when {
                matchWrongSymbols.isNotEmpty() -> {
                    textInput.isErrorEnabled = true
                    textInput.error = "Неверные символы: $matchWrongSymbols"
                    val color = ColorStateList.valueOf(Color.parseColor("#26EB5757"))
                    textInput.editText?.backgroundTintList = color
                }

                s.isNotEmpty() && !matchResult -> {
                    textInput.isErrorEnabled = true
                    textInput.error = "Формат почты должен быть x@x.xx"
                    val color = ColorStateList.valueOf(Color.parseColor("#26EB5757"))
                    textInput.editText?.backgroundTintList = color
                }

                else -> {
                    textInput.isErrorEnabled = false
                    textInput.error = null
                    textInput.editText?.backgroundTintList = null
                }
            }
            // endregion [VALIDATION]
        }
    }

    override fun afterTextChanged(s: Editable) {
    }

    private fun getWrongSymbols(regEx: Regex, content: CharSequence?): String =
        regEx.findAll("$content")
            .map { it.value }
            .joinToString("")
            .toCharArray()
            .toSet()
            .joinToString(", ", transform = { "«$it»" })
}