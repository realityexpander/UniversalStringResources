package com.plcoding.universalstringresources

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class DynamicString(val value: String): UiText() {
        inner class SubNumber(val value2: Int): UiText() {

            @Composable
            override fun asString(): String {
                return value.toString() + value2.toString()
            }

            fun asString2(): String {
                return value.toString() + value2.toString()
            }
        }
    }

    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ): UiText()

    class DynamicNumber(val value: Int): UiText()


    @Composable
    open fun asString(): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> stringResource(resId, *args)
            is DynamicNumber -> value.toString()
            is DynamicString.SubNumber -> this.asString()
        }
    }

    open fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
            is DynamicNumber -> value.toString()
            is DynamicString.SubNumber -> this.asString2()
        }
    }
}
