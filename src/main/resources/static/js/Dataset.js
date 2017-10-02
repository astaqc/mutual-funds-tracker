(function (w) {

    function Dataset(dataset) {
        dataset = dataset || {};
        this.label = dataset.label;
        this.borderColor = dataset.borderColor;
        this.backgroundColor = dataset.backgroundColor;
        this.fill = dataset.fill;
        this.data = dataset.data || [];
        // this.yAxisID = dataset.yAxisID;
        this.date = dataset.date;
    }



    w.Dataset = Dataset;
    w.Dataset.fromGroup = function (group) {
        var color = randomColor();
        return new Dataset({
            label: group.name,
            borderColor: color,
            backgroundColor: color,
            fill: false,
            data: group.data || [],
            yAxisID: group.yAxisID,
            date: group.date
        })
    };

})(window);