$(document).ready(function() {
  
  $('#inspect-btn').click(function() {
    var message = $('#random-user-message').val();
    if (message.length < 1) {
      return;
    }

    disableBtn('#inspect-btn');
    var json = {
      "message" : message
    };
    inspect(JSON.stringify(json));
  });

  var inspect = function(jsondata) {
    console.log('POST /inspect');
    console.log(JSON.stringify(jsondata));
    $.ajax({
      url : '/inspect',
      data : jsondata,
      type : 'post',
      cache : false,
      complete : function(data) {
        if (status === 'error' || !data.responseText) {
          console.log('Error');
        } else {
          console.log(data);
          var responseJson = data.responseJSON;
          $('#message').val(responseJson.message);
          createTable(responseJson.inspectResult)

        }
        resetDeIdentifyButton();
        $('#dlp-inspection-result-div *').addClass('visible');
      }
    });
  };

  function disableBtn(btn) {
    var loading = '<i class="fa fa-spinner fa-pulse fa-1x fa-fw"></i>'
    $(btn).html(loading);
    $(btn).attr('disabled', 'disabled');
  }
  ;

  function resetDeIdentifyButton(button) {
    $('#inspect-btn').html('DeIdentify');
    $('#inspect-btn').removeAttr('disabled');
  };
  
  function createTable(response) {
	$('#inspect-table').empty();
	$('#inspect-table').html('<tr><th>Quote</th><th>InfoType</th><th>Likelihood</th></tr>');
    $.each(response, function(i, item) {
        var $tr = $('<tr>').append(
            $('<td>').text(item.quote),
            $('<td>').text(item.infoType),
            $('<td>').text(item.likelihood)
        ); //.appendTo('#records_table');
        $('#inspect-table').append($tr);
    });
    
    $('#inspection-div *').addClass('visible');
    
};

});