package com.kangce.finance.ui.fixedassets.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.kangce.finance.R
import kotlinx.android.synthetic.main.dialog_date_picker.*
import java.text.SimpleDateFormat
import java.util.*

class ChoiceDateDialog :Dialog{

    val dateFormat by lazy { SimpleDateFormat("yyyy-MM-dd") }


    constructor(context: Context) : super(context, R.style.MyDialogStyle) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_date_picker)

        dp_wheel.minDate = dateFormat.parse("2017-01-01").time
        dp_wheel.maxDate = dateFormat.parse("2100-01-01").time

    }

}
