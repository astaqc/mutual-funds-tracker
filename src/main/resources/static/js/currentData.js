define('currentData', function () {
    var lineChartData = {
        labels: [],
        datasets: []
    };
    var cachedDatasets = [];

    function setCache(data) {
        data = data || [];
        if (cachedDatasets.length === 0) {
            data.forEach(function (t) {
                cachedDatasets.push(t)
            });
        }
    }

    function setDatasets(data) {
        setCache(data);
        lineChartData.datasets.splice(0);
        data.forEach(function (t) {
            lineChartData.datasets.push(t);
        });
    }

    function setLabels(labels) {
        lineChartData.labels = labels;
    }

    function resetData() {
        lineChartData.datasets = cachedDatasets;
    }

    function getDatasets() {
        return lineChartData.datasets;
    }

    function getOriginalDatasets() {
        return cachedDatasets;
    }

    function getDataFor(idx, day) {
        var dataset = lineChartData.datasets[idx];
        return {
            value: dataset.data[day],
            variation: dataset.variations[day]
        };
    }

    return {
        getDataSets: getDatasets,
        getLineChartData: function () {
            return lineChartData;
        },
        setDatasets: setDatasets,
        setLabels: setLabels,
        getOriginalDatasets: getOriginalDatasets,
        getDataFor: getDataFor

    }
});