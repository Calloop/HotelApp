package com.example.hotelapp.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class PhoneWatcher(private val editText: EditText) : TextWatcher {
    private val mask = "(###) ###-##-##"

    private var isFormatting = false
    private var isDeleting = false

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (isFormatting || isDeleting) {
            return
        }

        isFormatting = true

        val str = s.replace("\\D".toRegex(), "")
        val formatted = StringBuilder()
        var index = 0
        for (i in mask) {
            if (index >= str.length) {
                break
            }
            if (i == '#') {
                formatted.append(str[index])
                index++
            } else {
                formatted.append(i)
            }
        }

        editText.setText(formatted)
        editText.setSelection(formatted.length)

        isFormatting = false
    }

    override fun afterTextChanged(s: Editable) {
    }
}