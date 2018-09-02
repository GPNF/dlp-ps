
<%
	java.util.Locale requestLocale = request.getLocale();
	app.service.TextTranslator textTranslator = new app.service.TextTranslator(requestLocale.getLanguage());
%>

<!-- Nav Header -->
<div id="top-header">
  <nav class="navbar navbar-expand-md navbar-dark"
    style="background-color: black;">
    <button class="navbar-toggler navbar-toggler-right" type="button"
      data-toggle="collapse" data-target="#navbarNavAltMarkup"
      aria-controls="navbarNavAltMarkup" aria-expanded="false"
      aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href="/"><b> <%=textTranslator.translate("GNF")%>
    </b></a>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div class="navbar-nav">
        <a id="message-source" class="nav-item nav-link"
          href="/pages/MessageSource.jsp"> <%=textTranslator.translate("Message Source")%></a>
        <a id="publish" class="nav-item nav-link" href="/pages/InspectAndPublish.jsp"> <%=textTranslator.translate("Publish Message")%>
        </a><a id="pull" class="nav-item nav-link"
          href="/pages/syncpullclient.jsp"> <%=textTranslator.translate("Pull Client")%>
        </a> 
        
        <a id="user-pref-data" class="nav-item nav-link"
          href="/pages/dbtables/user-details-table.jsp"> 
          <%=textTranslator.translate("User Preference")%>
        </a>
        
        <select
          style="background-color: black; color: rgba(255, 255, 255, .5) !important; border: none; cursor: pointer;"
          onchange="window.open(this.value,'_blank');this.value=this.options[0].text">
          <option class="invisible"><%=textTranslator.translate("Database Tables")%></option>
          <option value="/pages/dbtables/publishdata.jsp"><%=textTranslator.translate("Publisher")%></option>
          <option value="/pages/dbtables/pulldata.jsp"><%=textTranslator.translate("Subscriber")%></option>
          <option value="/pages/dbtables/activity-logging-table.jsp"><%=textTranslator.translate("Logging")%></option>
          <option value="/pages/dbtables/message-status-table.jsp"><%=textTranslator.translate("Message Status")%></option>
        </select>

      </div>
    </div>
    <div>


      <!--       <select style="background-color: black; color: white;"
        class="pull-right">
        <option>English</option>
        <option>Français</option>
        <option>Deutsch</option>
        <option>Español</option>
      </select> -->
    </div>

  </nav>
</div>
<script src="/parts/js/header.js"></script>