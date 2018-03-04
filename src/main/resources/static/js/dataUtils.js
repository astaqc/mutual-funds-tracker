define(['Dataset'], function (Dataset) {


    function toValuePerUnity(item) {
        return item.unitaryValue / 1000;
    }

    function toPercentage() {
        let previous;
        return item => {
            const result = previous ? (item.unitaryValue / previous - 1) * 100 : 0;
            previous = item.unitaryValue;
            return result;
        }
    }

    function byDate(a, b) {
        return a.date - b.date;
    }

    function mapToDataAndVariations(sortedGroup) {
        const func = toPercentage();
        return sortedGroup.reduce(function (acc, item) {
                acc.data.push(toValuePerUnity(item));
                acc.variations.push(func(item));
                return acc;
            },
            {data: [], variations: []}
        )
    }

    function mapToDataset(group, name) {
        const sorted = group.sort(byDate);
        const dataAndVariation = mapToDataAndVariations(sorted);
        return Dataset.fromGroup({
            name: name,
            data: dataAndVariation.data,
            variations: dataAndVariation.variations
        });
    }

    function addToGroup(group, item) {
        group[item.name] = group[item.name] || [];
        group[item.name].push(item);
        return group;
    }

    function groupByName(data) {
        data = data || [];
        return data.reduce(addToGroup, {});
    }

    function convertToChartData(data) {
        data = data || [];
        const groups = groupByName(data);
        return $.map(groups, mapToDataset);
    }

    function uniqueDays(days) {
        let seen = {};
        return days.filter(function (item) {
            return seen.hasOwnProperty(item.date) ? false : (seen[item.date] = true);
        });
    }

    function asc(a, b) {
        return a - b;
    }

    function toDate(key) {
        return new Date(key.date);
    }


    function mapToLabels(data) {
        data = data || [];
        return uniqueDays(data)
            .map(toDate)
            .sort(asc)
            .map(toFormattedDate);
    }

    function toFormattedDate(date) {
        return `${date.getDate()}/${date.getMonth() + 1}`;
    }

    return {
        convertToChartData: convertToChartData,
        mapToLabels: mapToLabels
    }

});