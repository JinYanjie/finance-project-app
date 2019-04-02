package com.kangce.finance.widget

import android.text.Editable
import android.text.TextWatcher

//集成TextWatcher，避免多次复写 后面两个方法
interface MyTextWatcher : TextWatcher {

    override fun afterTextChanged(s: Editable?)

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

}