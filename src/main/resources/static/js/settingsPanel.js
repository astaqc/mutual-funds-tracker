define(['TrustStatusClient', 'currentChart', 'settingsTable', 'currentData',
        'dataUtils', 'calculatorPanel', 'constants'],
    (FundClient, currentChart, table, currentData, dataUtils, calcPanel, constants) => {

        let settingsPanel;

        function setData(response) {
            currentData.setDatasets(dataUtils.convertToChartData(response));
            currentData.setLabels(dataUtils.mapToLabels(response));
            currentChart.refreshChart();
            table.refreshData();
        }

        function fetchLastMonthData() {
            FundClient.getLastMonth(setData);
        }

        function fetchDataSince() {
            const date = getDateValue('#sinceDateInput');
            FundClient.getSince(date, setData);
        }

        function setSelected(e, dt, type, indexes) {
            currentData.setDatasets(table.getSelectedRows());
            currentChart.refreshChart();
        }

        function unsetLastDeselected(e, dt, type, indexes) {
            const selectedData = table.getSelectedRows();
            const dataToSet = selectedData.length === 0 ? currentData.getOriginalDatasets() : selectedData;
            currentData.setDatasets(dataToSet);
            currentChart.refreshChart();
        }

        function setupSettings() {
            $("#getLastMonthData").on('click', fetchLastMonthData);
            $("#getDataSince").on('click', fetchDataSince);

            table
                .initTable($('#trusts'))
                .on('select', setSelected)
                .on('deselect', unsetLastDeselected);
        }

        const panelConfig = {
            position: {my: "right", at: "center-top", offsetY: 5},
            contentSize: {width: 600, height: 700},
            theme: constants.panelTheme,
            headerToolbar: [
                {
                    item: "<span class='fa fa-calculator pointer'>",
                    event: 'click',
                    callback: calcPanel.openCalculatorPanel
                }
            ],
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
                done: setupSettings
            }
        };

        function close(panel) {
            if (panel) {
                panel.close();
            }
        }

        function getDateValue(query) {
            let date = $(query).val().split('-');
            return new Date(date);
        }

        return {
            openSettingsPanel: function () {
                close(settingsPanel);
                settingsPanel = $.jsPanel(panelConfig);
                fetchLastMonthData();
            }
        };
    });