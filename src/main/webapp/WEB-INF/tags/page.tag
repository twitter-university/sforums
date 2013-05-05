<%@ tag body-content="scriptless"%>
<%@ attribute name="title" required="true" rtexprvalue="true" %>
<%@ attribute name="nav" required="false" rtexprvalue="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>

<!DOCTYPE html>
<html>
  <head>
    <title>${title}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<c:url value='/css/bootstrap.min.css'/>" type="text/css" rel="stylesheet" />
    <link href="<c:url value='/css/bootstrap-responsive.min.css'/>" type="text/css" rel="stylesheet" />
    <link href="<c:url value='/css/style.css'/>" type="text/css" rel="stylesheet" />    
    <script src="<c:url value='/js/jquery-1.9.1.min.js'/>"></script>
    <script src="<c:url value='/js/bootstrap.min.js'/>"></script>
    <script src="<c:url value='/js/functions.js'/>"></script>
  </head>
  <body>    
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="brand" href="<c:url value='/home.html'/>">Marakana SForums</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="${nav eq 'add_category'? 'active' : ''}"><a href="<c:url value='/category_add.html'/>">Add Category</a></li>
              <li class="${nav eq 'categories'? 'active' : ''}"><a href="<c:url value='/categories.html'/>">List Categories</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="page-header">
        <h1>${title}</h1>
      </div>
      <jsp:doBody/>
    </div>
  </body>
</html>