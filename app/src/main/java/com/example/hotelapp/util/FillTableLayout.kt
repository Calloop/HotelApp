package com.example.hotelapp.util

import android.content.Context
import android.graphics.Color
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

class FillTableLayout(private val context: Context) {

    fun createTableRow(map: Map<String, String>): TableRow {
        val tableRow = TableRow(context)

        for ((key, value) in map) {
            val textViewKey = TextView(context).apply {
                text = key
            }

            val textViewValue = TextView(context).apply {
                text = value
            }

            tableRow.addView(textViewKey)
            tableRow.addView(textViewValue)
        }

        return tableRow
    }

    fun setTextViewAttributes(
        tableLayout: TableLayout,
        textAlignmentStart: Boolean,
        firstColumnWeight: Boolean
    ) {
        val rowCount = tableLayout.childCount

        (0..<rowCount).forEach { i ->
            val row = tableLayout.getChildAt(i).apply {
                setPadding(0, 16, 0, 16);
            }

            if (row is TableRow) {
                val columnCount = row.childCount

                (0..<columnCount).forEach { j ->
                    val view = row.getChildAt(j)

                    if (view is TextView) {
                        view.apply {
                            textSize = 16F

                            if (j % 2 == 0) {
                                setTextColor(Color.parseColor("#828796"))
                                textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START

                                layoutParams = if (firstColumnWeight) {
                                    TableRow.LayoutParams(
                                        0,
                                        TableRow.LayoutParams.WRAP_CONTENT,
                                        0.6f
                                    )
                                } else {
                                    TableRow.LayoutParams(
                                        0,
                                        TableRow.LayoutParams.WRAP_CONTENT,
                                        0.4f
                                    )
                                }
                            } else {
                                setTextColor(Color.parseColor("#000000"))

                                textAlignment = if (textAlignmentStart) {
                                    TextView.TEXT_ALIGNMENT_TEXT_START
                                } else {
                                    TextView.TEXT_ALIGNMENT_TEXT_END
                                }

                                layoutParams = if (firstColumnWeight) {
                                    TableRow.LayoutParams(
                                        0,
                                        TableRow.LayoutParams.WRAP_CONTENT,
                                        0.4f
                                    )
                                } else {
                                    TableRow.LayoutParams(
                                        0,
                                        TableRow.LayoutParams.WRAP_CONTENT,
                                        0.6f
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}