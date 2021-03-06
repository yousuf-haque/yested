package net.yested

import net.yested.utils.isIncludedInDOM

 data class PieChartSeries(
        val value: Number,
        val color: String,
        val highlight: String,
        val label: String) {}

 data class LineChartSeries(
       val label: String,
       val fillColor: String,
       val strokeColor: String,
       val pointColor: String,
       val pointStrokeColor: String,
       val pointHighlightFill: String,
       val pointHighlightStroke: String,
       val data: Array<Double>) {}

 data class LineChartData(
        val labels: Array<String>,
        val datasets: Array<LineChartSeries>) {}

 data class BarChartSeries(
        val label: String,
        val fillColor: String,
        val strokeColor: String,
        val highlightFill: String,
        val highlightStroke: String,
        val data: Array<Double>) {}

 data class BarChartData(
        val labels: Array<String>,
        val datasets: Array<BarChartSeries>) {}

 data class RadarChartSeries(
        val label: String,
        val fillColor: String,
        val strokeColor: String,
        val pointColor: String,
        val pointStrokeColor: String,
        val pointHighlightFill: String,
        val pointHighlightStroke: String,
        val data: Array<Double>) {}

 data class RadarChartData(
        val labels: Array<String>,
        val datasets: Array<RadarChartSeries>) {}

 data class PolarAreaChartSeries(
        val value: Number,
        val color: String,
        val highlight: String,
        val label: String) {}

private @native class ChartNative() {
     fun Pie(data: Array<PieChartSeries>, options: Any?): dynamic = noImpl;
     fun Doughnut(data: Array<PieChartSeries>, options: Any?): dynamic = noImpl;
     fun Line(data: LineChartData, options: Any?): dynamic = noImpl;
     fun Bar(data: BarChartData, options: Any?): dynamic = noImpl;
     fun Radar(data: RadarChartData, options: Any?): dynamic = noImpl;
     fun PolarArea(data: Array<PolarAreaChartSeries>, options: Any?): dynamic = noImpl;
}

private @native("new Chart") fun jsChart(context: Context): ChartNative = ChartNative();

 class Chart(width: Int, height: Int) : Canvas(width, height) {

    private fun drawChart(draw:(ChartNative)->dynamic, chartHandler:Function1<dynamic, Unit>?) {
        val generatedChart = draw(jsChart(getContext("2d")))
        if (chartHandler != null) {
            chartHandler(generatedChart)
        }
    }

    private fun drawChartWhenPossible(options: dynamic, chartHandler:Function1<dynamic, Unit>?, draw:(ChartNative)->dynamic) {
        if (options != null && options.responsive) {
            repeatWithDelayUntil(
                    check = { isIncludedInDOM(this.element) },
                    millisecondInterval = 100,
                    run = { drawChart(draw, chartHandler) })
        } else {
            return drawChart(draw, chartHandler)
        }
    }

     fun drawPieChart(data: Array<PieChartSeries>, options: Any? = null, chartHandler:Function1<dynamic, Unit>? = null) {
        drawChartWhenPossible(options, chartHandler) {
            it.Pie(data, options)
        }
    }

     fun drawDoughnutChart(data: Array<PieChartSeries>, options: Any? = null, chartHandler:Function1<dynamic, Unit>? = null) {
        drawChartWhenPossible(options, chartHandler) {
            it.Doughnut(data, options)
        }
    }

     fun drawLineChart(data: LineChartData, options: Any? = null, chartHandler:Function1<dynamic, Unit>? = null) {
        drawChartWhenPossible(options, chartHandler) {
            it.Line(data, options)
        }
    }

     fun drawBarChart(data: BarChartData, options: Any? = null, chartHandler:Function1<dynamic, Unit>? = null) {
        drawChartWhenPossible(options, chartHandler) {
            it.Bar(data, options)
        }
    }

     fun drawRadarChart(data: RadarChartData, options: Any? = null, chartHandler:Function1<dynamic, Unit>? = null) {
        drawChartWhenPossible(options, chartHandler) {
            it.Radar(data, options)
        }
    }

     fun drawPolarAreaChart(data: Array<PolarAreaChartSeries>, options: Any? = null, chartHandler:Function1<dynamic, Unit>? = null) {
        drawChartWhenPossible(options, chartHandler) {
            it.PolarArea(data, options)
        }
    }

}

