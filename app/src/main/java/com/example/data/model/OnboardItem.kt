package com.example.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnboardItem(
    @DrawableRes val imageRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int
)
