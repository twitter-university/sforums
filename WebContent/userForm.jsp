<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
  <head>
    <title>User Form</title>
    <jsp:include page="htmlHead.jsp"/>
  </head>
  <body>
    <jsp:include page="navBar.jsp"/>
    <h1>User Form</h1>
    <form method="post" action="<c:url value='/user_save.html'/>">
      <table>
        <c:if test="${not empty error}">
          <tr>
            <th>Error:</th>
            <td class="error">${error}</td>
          </tr>
        </c:if>
        <tr>
          <th>First Name *:</th>
          <td>
            <input type="text" name="firstName" value="${user.firstName}"/>
          </td>
        </tr>
        <tr>
          <th>Last Name *:</th>
          <td>
            <input type="text" name="lastName" value="${user.lastName}"/>
          </td>
        </tr>
        <tr>
          <th>Organization:</th>
          <td>
            <input type="text" name="organization" value="${user.organization}"/>
          </td>
        </tr>
        <tr>
          <th>Title:</th>
          <td>
            <input type="text" name="title" value="${user.title}"/>
          </td>
        </tr>
        <tr>
          <th>Email *:</th>
          <td>
            <input type="text" name="email" value="${user.email}"/>
          </td>
        </tr>
        <tr>
          <th>Password:</th>
          <td>
            <input type="password" name="password"/>
          </td>
        </tr>
        <tr>
          <th>Password Verification:</th>
          <td>
            <input type="password" name="passwordVerification"/>
          </td>
        </tr>
        <tr>
          <th>&nbsp;</th>
          <td>
            <input type="hidden" name="id" value="${user.id}"/>
            <input type="submit" value="Save"/>
          </td>
        </tr>
      </table>
    </form>
  </body>
</html>