package ru.netology.customview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.customview.ui.StatsView



class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = findViewById<StatsView>(R.id.stats)
        view.postDelayed(
            {
                view.data = listOf(
                    0.25F,
                    0.25F,
                    0.25F,
                    0.25F,
                )
            },2000)
    }
}