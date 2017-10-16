define(['Dataset', 'chartConfig', 'dataUtils', 'currentData'],
    (Dataset, chartConfig, utils, currentData) => {

        let myLine;

        function refreshChart() {
            console.log("refreshing");
            myLine.update();
        }

        function initChart(data) {
            myLine = chartConfig.initChart(data);
        }

        return {
            refreshChart: refreshChart,
            initChart: initChart
        };
    });