package com.plcoding.universalstringresources

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class DynamicString(val value: String): UiText() {

        inner class SubNumber(val subNumber: Int): UiText() {

            fun subNumberToString(): String {
                return value + subNumber.toString()
            }
        }
    }

    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ): UiText()

    class DynamicNumber(val value: Int): UiText() {
        fun dynamicNumberToString(): String = value.toString()
    }


    @Composable
    open fun asString(): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> stringResource(resId, *args)
            is DynamicNumber -> this.dynamicNumberToString()
            is DynamicString.SubNumber -> this.subNumberToString()
        }
    }

    open fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
            is DynamicNumber -> this.dynamicNumberToString()
            is DynamicString.SubNumber -> this.subNumberToString()
        }
    }
}
