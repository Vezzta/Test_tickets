package com.example.test_overcome.utils

import androidx.core.text.HtmlCompat

object Extensions {
    fun String.isEmail():Boolean{
        return this.matches(Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"))
    }

    fun String.isNumber():Boolean{
        return this.matches(Regex("[0-9]+"))
    }

    fun String.isInteger():Boolean{
        return  this.matches(Regex("-?[0-9]+"))
    }

    fun String.readHTML():String{
        return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
    }
}