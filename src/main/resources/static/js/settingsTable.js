define(['currentData'], function (currentData) {

    var table;
    var domElement;
    var dataTableConfig = {
        data: currentData.getDataSets(),
        scrollY: 700,
        deferRender: true,
        scroller: true,
        columnDefs: [
            {
                orderable: false,
                className: "select-checkbox",
                targets: 0
            }
        ],
        select: {
            style: "multi"
        },
        columns: [
            {title: "Color", data: printColor},
            {title: "Name", data: "label"}
        ]
    };

    function printColor(row) {
        return (
            '<i style="font-size: 2em;color: ' +
            row.backgroundColor +
            ';" >&#9673;</i>'
        );
    }

    function getSelectedRows() {
        return table
            .rows({selected: true})
            .data()
            .toArray();
    }

    function initTable(domElem) {
        domElement = domElem;
        updateConfigWithData();
        table = domElement
            .DataTable(dataTableConfig);
        return table;
    }

    function updateConfigWithData() {
        dataTableConfig.data = currentData.getDataSets();
    }

    function refreshData() {
        domElement.dataTable().fnDestroy();
        initTable(domElement);
    }

    return {
        initTable: initTable,
        getSelectedRows: getSelectedRows,
        refreshData: refreshData
    }

});