/*
 * Hits the GET URL. The JSON response is used for populating 
 * data into table on JSP page.
 * 
 * Also, handles the refresh icon show/hide
 * 
 * */

$(document).ready(function() {
	
	$('#loading-div, #loading').addClass('visible').hide().delay(500).fadeIn(300);

	$(document).ready(function() {
	    $('#publish-datatable').DataTable( {
	        "ajax": "/publishdata",
	        "order": [[ 3, 'desc' ]],
	        "columns": [
	            { "data": "messageId" },
	            { "data": function(d){
	            	var psMsgData = JSON.stringify(d);
	            	return JSON.parse(psMsgData).message.replace(/\n/g,"<br>");
	            	} 
	            },
	            { "data": "topicName" },
	            { "data": "publishTime" },
	            { "data": "globalTransactionId" }
	        ],
	        "initComplete": function(settings, json) {
	        	$('#loading').remove();
	            $('#publish-data *').addClass('visible').hide().fadeIn(500);
	          },
	        mark:true
	    } );
	} );

});