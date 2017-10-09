define(['TrustStatusClient', 'currentChart', 'settingsTable', 'currentData','dataUtils'],
    function (TrustStatusClient, currentChart, table, currentData, dataUtils) {

        var settingsPanel;

        function setData(response) {
            currentData.setDatasets(dataUtils.convertToChartData(response));
            currentData.setLabels(dataUtils.mapToLabels(response));
            currentChart.refreshChart();
            table.refreshData();
        }

        function fetchLastMonthData() {
            TrustStatusClient.getLastMonth(setData);
        }

        function fetchDataSince() {
            var date = $("#sinceDateInput")[0].valueAsDate;
            TrustStatusClient.getSince(date, setData);
        }

        function setSelected(e, dt, type, indexes) {
            currentData.setDatasets(table.getSelectedRows());
            currentChart.refreshChart();
        }

        function unsetLastDeselected(e, dt, type, indexes) {
            var selectedData = table.getSelectedRows();
            var dataToSet = selectedData.length === 0 ? currentData.getOriginalDatasets() : selectedData;
            currentData.setDatasets(dataToSet);
            currentChart.refreshChart();
        }

        function initConfigElements() {
            this.content.css("padding", "15px");

            $("#getLastMonthData").on("click", fetchLastMonthData);
            $("#getDataSince").on("click", fetchDataSince);

            table
                .initTable($("#trusts"))
                .on("select", setSelected)
                .on("deselect", unsetLastDeselected);
        }

        var panelConfig = {
            position: {my: "right", at: "center-top", offsetY: 5},
            contentSize: {width: 600, height: 700},
            theme: "crimson",
            headerTitle: "Settings",
            headerControls: {
                close: "disable",
                maximize: "disable",
                minimize: false,
                normalize: false,
                smallify: false,
                controls: "all",
                iconfont: "jsglyph"
            },
            contentAjax: {
                url: "settings.html",
                autoload: true,
                autoresize: true,
                autoreposition: true,
                done: initConfigElements
            }
        };


        return {
            openSettingsPanel: function () {
                if (settingsPanel) {
                    settingsPanel.close();
                }
                settingsPanel = $.jsPanel(panelConfig);
                fetchLastMonthData();
            }
        };
    });