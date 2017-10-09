define([], function () {

    function Client() {
        this.baseUrl = '/api/trustStatus'
    }

    function logErrors(d) {
        console.log(d);
    }

    Client.prototype.getLastMonth = function (success, error) {
        $.get(
            this.baseUrl + '/lastMonth',
            null,
            success
        ).fail(error || logErrors);
    };

    Client.prototype.getSince = function (date, success, error) {
        $.get(
            this.baseUrl + '/since',
            {date: date},
            success
        ).fail(error || logErrors)
    };

    return new Client();
});