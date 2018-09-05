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

        "columns" : [ {
          "data" : "userId"
        }, {
          "data" : "groupId"
        }],
        "initComplete" : function(settings, json) {
          initCheckBoxEvent();
          $('#loading').detach();
          $('#group-membership-data *').addClass('visible').hide().fadeIn(500);
        },
        mark : true
      });

      function getCheckbox(preference,id) {
        var checked = "checked";
        var flag = preference === 'Yes';
        var html;
        if (!flag) {
          checked = "";
        }
        
        return '<label class="checkbox-container "><input id='+id+' type="checkbox" ' + checked + '><span class="checkmark"></span></label>'
        
       // return '<input id='+id+' type="checkbox" data-toggle="toggle" ' + checked + '>';
      }
   
      function initCheckBoxEvent(){
        
        $('input[type=checkbox]').change(function(data){
          var msg = 'Do you want to update the preference?';
          if(!confirm(msg)){
            this.checked = !this.checked;
            return;
          } 
          var checkboxId = data.target.id;
          var id = checkboxId.split("-")[1];
          var type = checkboxId.split("-")[2];
          var value = data.target.checked;
          var json = {"id":id, "type":type, "value":value};
          updateUserPreference(json);
        });
      }
      
      function updateUserPreference(json){
        
        $.post('/api/updateUserPreference', JSON.stringify(json), function(response){
          if(response!==undefined && response.rowsModified>0){
            //TODO Display Message
          }
          //$('#loading-div, #loading').removeClass('overlay, visible');
         // $('#loading-div, #loading').addClass('invisible');
        });
        
      };

    });
