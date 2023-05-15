package ru.netology.customview.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import ru.netology.customview.R
import ru.netology.customview.dto.Data
import ru.netology.customview.util.AndroidUtils
import kotlin.math.min
import kotlin.random.Random

class StatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): View(context,attrs, defStyleAttr, defStyleRes) {
    private var radius = 0F
    private var center = PointF(0F,0F)
    private var oval = RectF(0F,0F,0F,0F)

    private var lineWidth = AndroidUtils.dp(context,5F).toFloat()
    private var fontSize = AndroidUtils.dp(context, 40F).toFloat()
    private var colors = emptyList<Int>()
    private var totalColor = 0

    init {
        context.withStyledAttributes(attrs, R.styleable.StatsView){
            lineWidth = getDimension(R.styleable.StatsView_lineWidth, lineWidth)
            fontSize = getDimension(R.styleable.StatsView_fontSize, fontSize)
            val resId = getResourceId(R.styleable.StatsView_colors, 0)
            colors = resources.getIntArray(resId).toList()
            totalColor = getColor(R.styleable.StatsView_totalColor, 0)
        }
    }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = lineWidth
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = fontSize
    }

    var data = Data(emptyList(), 0F)
    set(value) {
        field = value
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = min(w,h) / 2F - lineWidth / 2
        center = PointF(w / 2F, h / 2F)
        oval = RectF(
            center.x - radius,
            center.y - radius,
            center.x + radius,
            center.y + radius
        )
    }

    override fun onDraw(canvas: Canvas) {
        if (data.list.isEmpty()){
            return
        }
        canvas.drawCircle(center.x, center.y, radius, paint.apply { color = totalColor })
        var startFrom = -90F
        var colorFirst = 0
        for ((index, datum) in data.list.withIndex()){
            val angle = 360F * datum / data.total
            paint.color = colors.getOrNull(index)?: randomColor()
            if (index == 0){
                colorFirst = paint.color
            }
            canvas.drawArc(oval, startFrom, angle, false, paint)
            startFrom += angle
        }
        canvas.drawPoint(center.x, center.y - radius, paint.apply { color = colorFirst })

        canvas.drawText("%.2f%%".format(data.list.sum() / data.total * 100), center.x,
        center.y + textPaint.textSize / 4,
        textPaint
        )
    }
    private fun randomColor() = Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())
}