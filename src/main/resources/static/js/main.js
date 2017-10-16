define([
    'currentChart',
    'settingsPanel',
    'currentData'
], (currentChart, settingsPanel, currentData) => {
    settingsPanel.openSettingsPanel();
    currentChart.initChart(currentData.getLineChartData());
});