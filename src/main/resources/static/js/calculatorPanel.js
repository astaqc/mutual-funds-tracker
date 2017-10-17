define(['constants', 'settingsTable', 'TrustStatusClient'], (constants, table, FundClient) => {

    let calcPanel;

    function getDateValue(query) {
        let date = $(query).val().split('-');
        return new Date(date);
    }

    function validate(data) {
        return true;// todo: complete this
    }

    function calculate() {
        const data = table.getSelectedRows();
        if (validate(data)) {
            let fundName = data[0].label;
            const initialInvestment = $('#amount-input').val();
            const startingDate = getDateValue('#starting-date-input');
            FundClient.getGain(fundName, initialInvestment, startingDate, updateResult)
        }
    }

    function updateResult(response) {
        $('#result')
            .empty()
            .append(`<p>Variation: ${response.percentGain}%</p>`)
            .append(`<p>Gained: ${response.realGain}</p>`)
            .append(`<p>Total: ${response.total}</p>`);
    }

    function close(panel) {
        if (panel) {
            panel.close();
        }
    }

    function setupCalculator() {
        $('#calculate-btn').on('click', calculate);
    }

    function openCalculatorPanel() {
        close(calcPanel);
        calcPanel = $.jsPanel({
            theme: constants.panelTheme,
            headerTitle: 'Calculator',
            headerControls: {
                maximize: "disable",
            },
            contentAjax: {
                url: "calculator.html",
                autoload: true,
                autoresize: true,
                autoreposition: true,
                done: setupCalculator
            }
        });
    }

    return {
        openCalculatorPanel: openCalculatorPanel
    }

});