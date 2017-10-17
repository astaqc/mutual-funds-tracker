define([], () => {

    class Client {
        constructor() {
            this.baseUrl = '/api/trustStatus';
        }

        getLastMonth(success, error) {
            this._get(
                '/lastMonth',
                null,
                success,
                error
            );
        }

        getSince(date, success, error) {
            this._get(
                '/since',
                {date: date},
                success,
                error
            );
        }

        getGain(fundName, initialInvestment, sinceDate, success, error) {
            this._get(
                '/gain',
                {fundName: fundName, initialInvestment: initialInvestment, since: sinceDate},
                success,
                error
            );
        }

        _get(endpoint, data, success, error) {
            $.get(
                this.baseUrl + endpoint,
                data,
                success
            ).fail(error || logErrors);
        }
    }

    function logErrors(d) {
        console.log(d);
    }

    return new Client();
});