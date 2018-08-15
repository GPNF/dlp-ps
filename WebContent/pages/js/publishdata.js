$(document).ready(function() {

	$(document).ready(function() {
	    $('#publish-datatable').DataTable( {
	        "ajax": "/publishdata",
	        "order": [[ 3, 'desc' ]],
	       
	        "columns": [
	            { "data": "messageId" },
	            { "data": function(d){
	            	var psMsgData = JSON.stringify(d);
	            	return JSON.parse(psMsgData).message;
	            	} 
	            },
	            { "data": "topicName" },
	            { "data": "publishTime" },
	            { "data": "globalTransactionId" }
	        ],
	        mark:true
	    } );
	} );

});