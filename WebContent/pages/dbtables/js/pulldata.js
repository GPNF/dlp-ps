/*
 * Hits the GET URL. The JSON response is used for populating 
 * data into table on JSP page.
 * 
 * Also, handles the refresh icon show/hide
 * 
 * */

$(document).ready(function() {

  $('#loading-div, #loading').addClass('visible').hide().delay(500)
      .fadeIn(300);

  $('#pull-datatable')
      .DataTable(
          {
            "ajax" : {
              "url": "/pulldata",
              "data": function ( d ) {
                console.log(d);
              }
          }
                         ,
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
                    var message = JSON.parse(psMsgData).message.replace(/\n/g,"<br>");
                    
                    var result = '<span style="display:block;width:300px;word-wrap:break-word;">'
                      + message+
                  '</span>';
                    
                    return result;
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
                    var result = '<span style="display:block;width:200px;word-wrap:break-word;">'
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
