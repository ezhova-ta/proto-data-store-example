package com.forasoft.protodatastoreexample

data class AppSettings(
    var background: AppBackground?,
    var textColor: AppTextColor?,
    var textSize: Int?
)

enum class AppBackground {
    DARK, LIGHT
}

enum class AppTextColor {
    DARK, LIGHT
}
