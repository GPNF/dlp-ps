/*
 * This JS file is responsible for highlighting the active link 
 * in header on page load. It also add/remove active-link class 
 * when other link is clicked. Also, defines manual configuration 
 * in case the link URL is different from initial file name, the 
 * scenario when xyz.jsp makes request to servlet, returns back to 
 * same page but URL changes to /wxyz.  
 * 
 * */

$(document).ready(function() {
  var pathname = window.location.pathname;

  // Active link based on path.
  $('.navbar-nav > a[href="' + pathname + '"]').addClass('active');

  // Other than path, other urls also need to be handled
  if (pathname === '/') {
    $('#message-source').addClass('active');
  }
  if (pathname === '/pullmessage' || pathname === '/pulldata.jsp') {
    $('#pull').addClass('active');
  }

  if (pathname === '/notify' || pathname === '/pages/MessageSource.jsp') {
    $('#message-source').addClass('active');
  }

  if (pathname === '/dbtables/user-details-table.jsp') {
    $('#user-pref-data').addClass('active');
  }

  if (pathname === '/dbtables/activity-logging-table.jsp') {
    $('#activity-logging-data').addClass('active');
  }

  if (pathname === '/dbtables/message-status-table.jsp') {
    $('#message-status-data').addClass('active');
  }
  
  if(pathname === '/pages/InspectAndPublish.jsp'){
    $('#publish').addClass('active');
  }

  $('#top-header .navbar-nav a').click(function() {

    console.log('Clicked link on header');
    var foundElem = $('#top-header .navbar-nav').find('a.active');
    $.each(foundElem, function(index, value) {
      $(value).removeClass('active');
    });
    $(this).parent('a').addClass('active');
  });
});