package com.android.memorynotes.presentation.common

import android.view.View

interface ListActionListener {
    fun onClickListItem(id: Long, view: View)
}