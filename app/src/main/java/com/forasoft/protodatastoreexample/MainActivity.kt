package com.forasoft.protodatastoreexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var appSettingsDataStoreManager: AppSettingsDataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appSettingsDataStoreManager = AppSettingsDataStoreManager(applicationContext)
        initViews()
        observeAppSettings()
    }

    private fun initViews() {
        backgroundChipGroup?.setOnCheckedChangeListener { _, checkedId ->
            val background = when(checkedId) {
                R.id.darkBackground -> AppBackground.DARK
                R.id.lightBackground -> AppBackground.LIGHT
                else -> null
            } ?: return@setOnCheckedChangeListener

            lifecycleScope.launch {
                appSettingsDataStoreManager.updateBackground(background)
            }
        }

        textColorChipGroup?.setOnCheckedChangeListener { _, checkedId ->
            val textColor = when(checkedId) {
                R.id.darkTextColor -> AppTextColor.DARK
                R.id.lightTextColor -> AppTextColor.LIGHT
                else -> null
            } ?: return@setOnCheckedChangeListener

            lifecycleScope.launch {
                appSettingsDataStoreManager.updateTextColor(textColor)
            }
        }

        textSizeChipGroup?.setOnCheckedChangeListener { _, checkedId ->
            val textSize = when(checkedId) {
                R.id.smallTextSize -> 14
                R.id.mediumTextSize -> 24
                R.id.largeTextSize -> 32
                else -> 24
            }

            lifecycleScope.launch {
                appSettingsDataStoreManager.updateTextSize(textSize)
            }
        }
    }

    private fun observeAppSettings() {
        appSettingsDataStoreManager.appSettingsFlow.asLiveData().observe(this) {
            when(it.background) {
                AppBackground.DARK -> setDarkBackground()
                else -> setLightBackground()
            }

            when(it.textColor) {
                AppTextColor.LIGHT -> setLightTextColor()
                else -> setDarkTextColor()
            }

            setTextSize(it.textSize ?: 18)
        }
    }

    private fun setDarkBackground() {
        container?.setBackgroundResource(R.color.dark_gray)
    }

    private fun setLightBackground() {
        container?.setBackgroundResource(R.color.light_gray)
    }

    private fun setDarkTextColor() {
        contentText?.setTextColor(resources.getColor(R.color.light_gray, applicationContext.theme))
    }

    private fun setLightTextColor() {
        contentText?.setTextColor(resources.getColor(R.color.white, applicationContext.theme))
    }

    private fun setTextSize(size: Int) {
        contentText?.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.toFloat())
    }
}