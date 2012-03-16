<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
  <head>
    <title>${category.name} Category</title>
    <jsp:include page="htmlHead.jsp"/>
  </head>
  <body>
    <jsp:include page="navBar.jsp"/>
    <h1>${category.name} Category</h1>
    <table class="nameValuePairs">
      <tr>
        <th>Name:</th>
        <td>${category.name}</td>
      </tr>
      <tr>
        <th>Description:</th>
        <td>${f:convertToHtmlLineBreaks(category.description)}</td>
      </tr>
      <security:authorize ifAllGranted="ROLE_ADMIN">
	    <tr>
	      <th>&nbsp;</th>
	      <td style="padding-top: 10px;">
	        <a class="button" href="<c:url value='/category_form.html?id=${category.id}'/>">Edit</a>
	        <a class="button" href="<c:url value='/category_delete.html?id=${category.id}'/>"
		     onclick="return confirm('Are you sure you wish to delete this category?');">Delete</a>
	      </td>
	    </tr>
      </security:authorize>
    </table>
  </body>
</html>