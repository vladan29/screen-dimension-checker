package com.vladan.screen_dimension_checker

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.vladan.screen_dimension_checker.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt


class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var widthPx: Int = -1
    private var heightPx: Int = -1

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.hide()

        val displayMetrics = resources.displayMetrics
        val densityScaleFactor = displayMetrics.density
        val densityOfScreen = displayMetrics.densityDpi


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
       val  currentScreen  = windowManager.currentWindowMetrics
                widthPx= currentScreen.bounds.width()
                 heightPx = currentScreen.bounds.height()
        } else {
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            heightPx= displayMetrics.heightPixels
            widthPx = displayMetrics.widthPixels
        }

        val widthInches: Double = widthPx/displayMetrics.xdpi.toDouble()
        val heightInches: Double = heightPx/displayMetrics.ydpi.toDouble()
        val widthAnHeightPowSum = widthInches.pow(2) + heightInches.pow(2)
        val screenDiagonal = sqrt(widthAnHeightPowSum).toPrecision(2).toDouble()
        val model = Build.MODEL
        val manufacturer = Build.MANUFACTURER
        val text = "DENSITY SCALE FACTOR: $densityScaleFactor\n\n" +
                "SCREEN DENSITY: $densityOfScreen\n\n" +
                "SCREEN HEIGHT PX: $heightPx\n" +
                "SCREEN WIDTH PX: $widthPx\n\n" +
                "SCREEN HEIGHT INCH: $heightInches\n" +
                "SCREEN WIDTH INCH: $widthInches\n\n" +
                "SCREEN DIAGONAL INCH: $screenDiagonal\n\n" +
                "MODEL: $model\n" +
                "MANUFACTURER: $manufacturer"

        binding.vTestView.text = text
    }

    private fun Double.toPrecision(precision: Int) =
        if (precision < 1) {
            "${this.roundToInt()}"
        } else {
            val p = 10.0.pow(precision)
            val v = (abs(this) * p).roundToInt()
            val i = floor(v / p)
            var f = "${floor(v - (i * p)).toInt()}"
            while (f.length < precision) f = "0$f"
            val s = if (this < 0) "-" else ""
            "$s${i.toInt()}.$f"
        }
}