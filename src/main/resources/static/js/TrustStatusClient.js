define([], () => {

    class Client {
        constructor() {
            this.baseUrl = '/api/trustStatus';
        }

        getLastMonth(success, error) {
            $.get(
                this.baseUrl + '/lastMonth',
                null,
                success
            ).fail(error || logErrors);
        };

        getSince(date, success, error) {
            $.get(
                this.baseUrl + '/since',
                {date: date},
                success
            ).fail(error || logErrors)
        };
    }

    function logErrors(d) {
        console.log(d);
    }

    return new Client();
});