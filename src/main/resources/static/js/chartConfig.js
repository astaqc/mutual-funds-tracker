define(['currentData'], function (currentData) {

    function buildLabel(tooltipItem, data) {
        return 'Value: ' + currentData.getDataFor(
            tooltipItem.datasetIndex,
            tooltipItem.index
        ).value;
    }

    function buildAfterLabel(tooltipItem, data) {
        return 'Variation: ' + currentData.getDataFor(
            tooltipItem.datasetIndex,
            tooltipItem.index
        ).variation;
    }

    function buildTitle(tooltips, data) {
        var tooltip = tooltips[0];
        return data.labels[tooltip.index] + '  ' +
            data.datasets[tooltip.datasetIndex].label;
    }


    return {
        initChart: function (lineChartData) {
            var ctx = document.getElementById("canvas").getContext("2d");
            return Chart.Line(ctx, {
                data: lineChartData,
                options: {
                    tooltips: {
                        callbacks: {
                            label: buildLabel,
                            afterLabel: buildAfterLabel,
                            title: buildTitle
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
                        xAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: "day"
                            }
                        }],
                        yAxes: [{
                            type: "linear",
                            display: true,
                            position: "left"
                            // id: "y-axis-1"
                        }]
                    }
                }
            });
        }

    };
});