package com.plcoding.universalstringresources

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class DynamicString(val value: String): UiText() {

        override fun toString(): String {
            return value
        }

        inner class SubNumber(val subNumber: Int): UiText() {

            override fun toString(): String {
                return "$value.$subNumber"
            }
        }
    }

    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ): UiText()

    class DynamicNumber(val value: Int): UiText() {
        override fun toString(): String = value.toString()
    }


    @Composable
    open fun asString(): String {
        return when(this) {
            is DynamicString -> this.value
            is DynamicString.SubNumber -> this.toString()
            is StringResource -> stringResource(resId, *args)
            is DynamicNumber -> this.toString()
        }
    }

//    open fun asString(context: Context): String {
    open fun asString(context: Context?): String {
        return when(this) {
            is DynamicString -> this.value
            is DynamicString.SubNumber -> this.toString()
//            is StringResource -> context.getString(resId, *args)
            is StringResource -> context?.getString(resId, *args) ?: ""
            is DynamicNumber -> this.toString()
        }
    }
}

fun main() {
    val x = UiText.DynamicString("hello").SubNumber(2)
    println(x.asString(null))

    val y = UiText.DynamicString("heya")
    println(y.asString(null))
}