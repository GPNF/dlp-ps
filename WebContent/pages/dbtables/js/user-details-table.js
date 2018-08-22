$(document).ready(
    function() {
      console.log('js loaded');
      $('#loading-div, #loading').addClass('visible').hide().delay(500).fadeIn(
          300);

      $('#userdetails-datatable').DataTable({
        "ajax" : "/userdetailsdata",
        columnDefs : [ {
          width : '20%',
          targets : 0
        } ],
        fixedColumns : true,

        "columns" : [ {
          "data" : "userId"
        }, {
          "data" : "userName"
        }, {
          "data" : "emailId"
        }, {
          "data" : "mobileNumber"
        }, {
          "data" : function(d) {
              return d.emailFlag;
              //return getCheckbox(d.emailFlag, 'email-checkbox');
          }
        }, {
          "data" : function(d) {
            return d.smsFlag;
              //return getCheckbox(d.smsFlag, 'sms-checkbox');
          }
        }, {
          "data" : function(d) {
            return d.phoneCallFlag;
              //return getCheckbox(d.phoneCallFlag, 'phone-call-checkbox');
          }
        } ],
        "initComplete" : function(settings, json) {
          $('#loading').remove();
          $('#userdetails-data *').addClass('visible').hide().fadeIn(500);
        },
        mark : true
      });

/*      function getCheckbox(preference,id) {
        var checked = "checked";
        var flag = preference === 'Yes';
        var html;
        if (!flag) {
          checked = "";
        }
        return '<input id='+id+' type="checkbox" data-toggle="toggle" ' + checked + '>';
      }*/

    });
