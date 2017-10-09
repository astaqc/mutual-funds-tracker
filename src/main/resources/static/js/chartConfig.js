define([], function () {
    return {
        initChart: function (lineChartData) {
            var ctx = document.getElementById("canvas").getContext("2d");
            return Chart.Line(ctx, {
                data: lineChartData,
                options: {
                    tooltips: {
                        callbacks: {
                            label: function (tooltipItem, data) {
                                var idx = tooltipItem.datasetIndex;
                                var day = tooltipItem.index;
                                return 'Value: ' + data.datasets[idx].data[day];
                            },
                            afterLabel: function (tooltipItem, data) {
                                var idx = tooltipItem.datasetIndex;
                                var day = tooltipItem.index;
                                return 'Variation: ' + data.datasets[idx].variations[day];
                            },
                            title: function (tooltips, data) {
                                var tooltip = tooltips[0];
                                return data.labels[tooltip.index] + '  ' +
                                    data.datasets[tooltip.datasetIndex].label;
                            }
                        }
                    },
                    legend: false,
                    responsive: true,
                    tooltip: {
                        mode: 'point',
                        intersect: true
                    },
                    hover: {
                        mode: 'point',
                        intersect: true
                    },
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