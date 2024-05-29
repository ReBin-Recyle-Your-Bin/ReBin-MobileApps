package com.bangkit.rebinmobileapps.view.customView

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doBeforeTextChanged
import com.bangkit.rebinmobileapps.R

class CustomTextEmail: AppCompatEditText, View.OnTouchListener {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttrs: Int) : super(context, attrs, defStyleAttrs) {
        init()
    }

    private fun init() {
        setOnTouchListener(this)

        addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty() && !isValidEmail(s)) error = context.getString(R.string.email_error_message)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return false
    }

    private fun isValidEmail(email: CharSequence?) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}












