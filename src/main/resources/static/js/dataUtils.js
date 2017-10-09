define(['Dataset'], function (Dataset) {


    function toTotalValue(item) {
        return item.valuesPerUnity;
    }

    function toPercentage() {
        var previous;
        return function (item) {
            var result = previous ? (item.valuesPerUnity / previous - 1) * 100 : 0;
            previous = item.valuesPerUnity;
            return result;
        }
    }

    function byDate(a, b) {
        return new Date(a.date) - new Date(b.date);
    }

    function mapToDataset(group, name) {
        var sorted = group.sort(byDate);
        return Dataset.fromGroup({
            name: name,
            data: sorted.map(toTotalValue),
            variations: sorted.map(toPercentage())
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
        var groups = groupByName(data);
        return $.map(groups, mapToDataset);
    }

    function uniqueDays(days) {
        var seen = {};
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
        return "" + date.getDate() + "/" + (date.getMonth() + 1);
    }

    return {
        convertToChartData: convertToChartData,
        mapToLabels: mapToLabels
    }

});