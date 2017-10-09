define([
    'currentChart',
    'settingsPanel',
    'currentData'
], function(currentChart, settingsPanel, currentData) {
    settingsPanel.openSettingsPanel();
    currentChart.initChart(currentData.getLineChartData());
});