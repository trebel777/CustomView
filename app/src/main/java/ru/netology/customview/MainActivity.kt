package ru.netology.customview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.customview.dto.Data
import ru.netology.customview.ui.StatsView

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<StatsView>(R.id.stats).data = Data(
            listOf(
                250F,
                250F,
                250F,


            ),
            1000F
        )
    }
}