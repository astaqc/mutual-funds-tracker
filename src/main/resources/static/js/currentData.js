define('currentData', () => {
    const lineChartData = {
        labels: [],
        datasets: []
    };
    const cachedDatasets = [];

    function setCache(data) {
        data = data || [];
        if (cachedDatasets.length === 0) {
            data.forEach(t => {
                cachedDatasets.push(t)
            });
        }
    }

    function setDatasets(data) {
        setCache(data);
        lineChartData.datasets.splice(0);
        data.forEach(t => {
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
        const dataset = lineChartData.datasets[idx];
        return {
            value: dataset.data[day],
            variation: dataset.variations[day]
        };
    }

    return {
        getDataSets: getDatasets,
        getLineChartData: () => lineChartData,
        setDatasets: setDatasets,
        setLabels: setLabels,
        getOriginalDatasets: getOriginalDatasets,
        getDataFor: getDataFor

    }
});