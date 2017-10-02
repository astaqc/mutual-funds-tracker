var lineChartData = {
    labels: [],
    datasets: []
};

var datasets;

(function () {

    function refreshChart() {
        console.log('refreshing');
        window.myLine.update();
    }

    function logErrors(d) {
        console.log(d);
    }

    function addToGroup(group, item) {
        group[item.name] = group[item.name] || [];
        group[item.name].push(item);
        return group;
    }

    function groupByName(data) {
        data = data || [];
        return data.reduce(addToGroup, {});
    }

    function toTotalValue(item) {
        return item.totalValue;
    }

    function initChart() {
        var ctx = document.getElementById("canvas").getContext("2d");
        window.myLine = Chart.Line(ctx, {
            data: lineChartData,
            options: {
                legend: false,
                responsive: true,
                hoverMode: 'index',
                // stacked: false,
                title: {
                    display: false,
                    text: 'Chart.js Line Chart - Multi Axis'
                },
                scales: {
                    xAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'day'
                        }
                    }],
                    yAxes: [{
                        type: "linear", // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
                        display: true,
                        position: "left"
                        // id: "y-axis-1",
                    }]
                }
            }
        });
    }

    function byDate(a, b) {
        return new Date(a.date) - new Date(b.date);

    }

    function reduceToDataset(group, name) {
        return {
            name: name,
            data: group.sort(byDate).map(toTotalValue)
        }
    }

    function mapToDataset(data) {
        data = data || [];
        var groups = groupByName(data);
        return $.map(groups, reduceToDataset)
            .map(Dataset.fromGroup);

    }

    function toDate(key) {
        return new Date(key.date);
    }

    function toFormattedDate(date) {
        return '' + date.getDate() + '/' + (date.getMonth() + 1);
    }

    function uniqueDays(days) {
        var seen = {};
        return days.filter(function (item) {
            return seen.hasOwnProperty(item.date) ? false : (seen[item.date] = true);
        });
    }

    function asc(a, b) {
        return a - b;
    }

    function mapToLabels(data) {
        data = data || [];
        return uniqueDays(data)
            .map(toDate)
            .sort(asc)
            .map(toFormattedDate);
    }

    function removeOne() {
        lineChartData.datasets.splice(0, 1)
    }

    function setDatasets(data) {
        lineChartData.datasets.splice(0);
        data.forEach(function (t) {
            lineChartData.datasets.push(t);
        });
    }

    function setLastMonthData(response) {
        datasets = mapToDataset(response);
        setDatasets(datasets);
        lineChartData.labels = mapToLabels(response);
        openSettingsPanel();
        refreshChart();
    }

    function getLastMonthData() {
        TrustStatusClient.getLastMonth(setLastMonthData, logErrors);
    }

    function printColor(row) {
        return '<i style="font-size: 2em;color: ' + row.backgroundColor + ';" >&#9673;</i>'
    }

    function getSelectedRows(dt) {
        return dt.table().rows({selected: true}).data().toArray();
    }

    function getDataSince() {
        var date = $('#sinceDateInput')[0].valueAsDate;
        TrustStatusClient.getSince(date, setLastMonthData, logErrors);
    }

    function initConfigElements() {
        this.content.css("padding", "15px");

        $('#getLastMonthData').on('click', getLastMonthData);
        $('#getDataSince').on('click', getDataSince);

        $('#trusts').DataTable({
            data: datasets,
            scrollY: 700,
            deferRender: true,
            scroller: true,
            columnDefs: [{
                orderable: false,
                className: 'select-checkbox',
                targets: 0
            }],
            select: {
                style: 'multi'
            },
            columns: [
                {title: "Color", data: printColor},
                {title: "Name", data: 'label'}
            ]
        }).on('select', function (e, dt, type, indexes) {
            setDatasets(getSelectedRows(dt));
            // addToDatasets(dt.data());
            refreshChart()
        }).on('deselect', function (e, dt, type, indexes) {
            var selectedData = getSelectedRows(dt);
            var dataToSet = selectedData.length === 0 ? datasets : selectedData;
            setDatasets(dataToSet);

            refreshChart()
        });
    }

    var settingsPanel;

    function openSettingsPanel() {
        if (settingsPanel) {
            settingsPanel.close();
        }
        settingsPanel = $.jsPanel({
            position: {my: "right", at: "center-top", offsetY: 5},
            contentSize: {width: 600, height: 700},
            theme: 'crimson',
            headerTitle: "Settings",
            headerControls: {
                close: 'disable',
                maximize: 'disable',
                minimize: false,
                normalize: false,
                smallify: false,
                controls: 'all',
                iconfont: 'jsglyph'
            },
            contentAjax: {
                url: "settings.html",
                autoload: true,
                autoresize: true,
                autoreposition: true,
                done: initConfigElements
            }
        });
    }

    window.onload = function () {
        initChart();
        getLastMonthData();
    };


})();

