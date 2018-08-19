$(document).ready(function() {

  $('#loading-div, #loading').addClass('visible').hide().delay(500)
      .fadeIn(300);

  $('#pull-datatable')
      .DataTable(
          {
            "ajax" : "/pulldata",
            "order" : [ [ 0, 'desc' ] ],
            columnDefs : [ {
              width : '20%',
              targets : 0
            } ],
            fixedColumns : true,

            "columns" : [
                {
                  "data" : function(d) {
                    var pullTimeData = JSON.stringify(d);
                    var pullTime = JSON.parse(pullTimeData).pullTime;
                    var result = '<span style="display:block;width:100px;word-wrap:break-word;">'
                        + pullTime
                    '</span>';
                    return result;
                  }

                },
                {
                  "data" : "publishTime"
                },
                {
                  "data" : "messageId"
                },
                {
                  "data" : function(d) {
                    var psMsgData = JSON.stringify(d);
                    return JSON.parse(psMsgData).message.replace(/\n/g,
                        "<br>");
                  }
                },
                {
                  "data" : "subscriptionId"
                },
                {
                  "data" : "globalTransactionId"
                },
                {
                  "data" : function(d) {
                    var ackIdData = JSON.stringify(d);
                    var ackId = JSON.parse(ackIdData).ackId;
                    var result = '<span style="display:block;width:270px;word-wrap:break-word;">'
                        + ackId
                    '</span>';
                    return result;
                  }
                } ],
            "initComplete" : function(settings, json) {
              $('#loading').remove();
              $('#pull-data *').addClass('visible').hide().fadeIn(500);
            },
            mark : true
          });
  
});
