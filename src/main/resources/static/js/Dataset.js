define('Dataset', () => {

    class Dataset {
        constructor(dataset) {
            dataset = dataset || {};
            this.label = dataset.label;
            this.borderColor = dataset.borderColor;
            this.backgroundColor = dataset.backgroundColor;
            this.fill = dataset.fill;
            this.data = dataset.data || [];
            this.date = dataset.date;
            this.yAxisID = dataset.yAxisID;
            this.variations = dataset.variations || [];
        }

        static fromGroup(group) {
            const color = randomColor();
            return new Dataset({
                label: group.name,
                borderColor: color,
                backgroundColor: color,
                fill: false,
                data: group.data || [],
                yAxisID: group.yAxisID,
                date: group.date,
                variations: group.variations
            })
        };

    }


    return Dataset;
});