$(document).ready(function(){

  console.log('js loaded');
  
  $('#msg-src-form').submit(function() {
    var loading = '<i class="fa fa-spinner fa-pulse fa-1x fa-fw"></i>'
    $('#submit-btn').html(loading);
    $('#submit-btn').attr('disabled', 'disabled');
});

}
);

