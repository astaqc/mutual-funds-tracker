define('Dataset',function () {

    function Dataset(dataset) {
        dataset = dataset || {};
        this.label = dataset.label;
        this.borderColor = dataset.borderColor;
        this.backgroundColor = dataset.backgroundColor;
        this.fill = dataset.fill;
        this.data = dataset.data || [];
        this.date = dataset.date;
    }

    Dataset.fromGroup = function (group) {
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

    return Dataset;
});