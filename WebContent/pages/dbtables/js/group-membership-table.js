/*
 * Hits the GET URL. The JSON response is used for populating 
 * data into table on JSP page.
 * 
 * Also, handles the refresh icon show/hide
 * 
 * */

$(document).ready(
    function() {
      console.log('js loaded');
      $('#loading-div, #loading').addClass('visible').hide().delay(500).fadeIn(
          300);

      $('#group-membership-datatable').DataTable({
        "ajax" : "/api/get-user-group-membership",
        columnDefs : [ {
          width : '20%',
          targets : 0
        } ],
        fixedColumns : true,

        "columns" : [ 
        {
          "data" : "groupId"
        }, {
          "data" : "groupName"
        },{
          "data" : "groupAuthLevel"
        },{
          "data" : "userId"
        },{
          "data" : "userName"
        }
        
        
        
        ],
        "initComplete" : function(settings, json) {
          $('#loading').detach();
          $('#group-membership-data *').addClass('visible').hide().fadeIn(500);
        },
        mark : true
      });

    });
