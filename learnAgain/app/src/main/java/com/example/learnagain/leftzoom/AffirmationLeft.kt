package com.example.learnagain.leftzoom

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Affirmation(
    @StringRes val StringResourceId: Int,
    @DrawableRes val ImageResourceId: Int) {
}
