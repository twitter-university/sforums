<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
  <head>
    <title>SForums - Login</title>
    <jsp:include page="htmlHead.jsp"/>
  </head>
  <body>
    <jsp:include page="navBar.jsp"/>
    <h1>Login to SForums</h1>
    <form action="<c:url value='/security_check.html'/>" method="post">
      <table>
        <c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
          <tr>
            <th>Error:</th>
            <td class="error">
              <strong>
                <spring:message code="loginError.${f:getType(sessionScope.SPRING_SECURITY_LAST_EXCEPTION)}"
                text="Your login attempt was not successful, please try again."/>
              </strong><br/>
              <spring:message code="loginErrorExtra.${f:getType(sessionScope.SPRING_SECURITY_LAST_EXCEPTION)}"
                text="Reason: ${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}"/>
              <c:set scope="session" var="SPRING_SECURITY_LAST_EXCEPTION" value="${null}"/>
            </td>
          </tr>
        </c:if>
        <tr>
          <th>Email:</th>
          <td>
            <input type="text"  name="j_username" id="j_username"
              value="${sessionScope.SPRING_SECURITY_LAST_USERNAME}" />
          </td>
        </tr>
        <tr>
          <th>Password:</th>
          <td><input type="password"  name="j_password" id="j_password" /></td>
        </tr>
        <tr>
          <th>Remember me:</th>
          <td>
            <input type="checkbox" name="_spring_security_remember_me"
            id="_spring_security_remember_me"/>
            <span class="small">(Remember me on this computer)</span>
          </td>
        </tr>
        <tr>
          <th>&nbsp;</th>
          <td><input type="submit" value="Login" /></td>
        </tr>
      </table>
    </form>
  </body>
</html>