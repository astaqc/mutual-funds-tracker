(function (window) {

    function Client() {
        this.baseUrl = '/api/trustStatus'
    }

    Client.prototype.getLastMonth = function (success, error) {
        $.get(this.baseUrl + '/lastMonth', null, success).fail(error);
    };

    Client.prototype.getSince = function (date, success, error) {
        $.get(this.baseUrl + '/since', {date: date}, success).fail(error)
    };


    window.TrustStatusClient = new Client();
    console.log('client loaded');
})(window);