define([], function () {
    return {
        initChart: function (lineChartData) {
            var ctx = document.getElementById("canvas").getContext("2d");
            return Chart.Line(ctx, {
                data: lineChartData,
                options: {
                    legend: false,
                    responsive: true,
                    hoverMode: "index",
                    title: {
                        display: false,
                        text: "Chart.js Line Chart - Multi Axis"
                    },
                    scales: {
                        xAxes: [
                            {
                                display: true,
                                scaleLabel: {
                                    display: true,
                                    labelString: "day"
                                }
                            }
                        ],
                        yAxes: [
                            {
                                type: "linear", // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
                                display: true,
                                position: "left"
                                // id: "y-axis-1",
                            }
                        ]
                    }
                }
            });
        }

    };
});