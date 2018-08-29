$(document).ready(
    function() {
      console.log('js loaded');
      $('#loading-div, #loading').addClass('visible').hide().delay(500).fadeIn(
          300);

      $('#logging-datatable').DataTable({
        "ajax" : "/api/getLoggingData",
        columnDefs : [ {
          width : '20%',
          targets : 0
        } ],
        fixedColumns : true,

        "columns" : [ {
          "data" : "autoIncrId"
        },
        {
          "data" : "messageId"
        }, {
          "data" : function(data){
        	  var html = '<span>'+data.messageData+'</span>'
        	  return html;
          }
        },
        {
          "data" : "subscriptionName"
        }, {
          "data" : "topicName"
        }, {
          "data" : "publishedTimestamp"
        }, {
          "data" : "globalTransactionId"
        }],
        "initComplete" : function(settings, json) {
          $('#loading').detach();
          $('#logging-data *').addClass('visible').hide().fadeIn(500);
        },
        mark : true
      });

    });
