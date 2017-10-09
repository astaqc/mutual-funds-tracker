define(['Dataset', 'chartConfig', 'dataUtils', 'currentData'], function (Dataset, chartConfig, utils, currentData) {

    var myLine;


    function refreshChart() {
        console.log("refreshing");
        myLine.update();
    }

    function initChart(data){
        myLine = chartConfig.initChart(data);
    }

    return {
        refreshChart: refreshChart,
        initChart: initChart
    };
});