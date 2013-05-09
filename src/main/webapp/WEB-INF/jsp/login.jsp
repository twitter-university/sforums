<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<tags:page title="Login to Marakana SForums">
  <c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
    <spring:message var="errorTitle" 
      code="loginError.${f:getType(sessionScope.SPRING_SECURITY_LAST_EXCEPTION)}" 
      text="Your login attempt was not successful, please try again."/>
    <spring:message var="errorMessage" 
      code="loginErrorExtra.${f:getType(sessionScope.SPRING_SECURITY_LAST_EXCEPTION)}" 
      text="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}"/>
    <c:set scope="session" var="SPRING_SECURITY_LAST_EXCEPTION" value="${null}"/>              
    <tags:alert type="error" title="${errorTitle}" message="${errorMessage}"/>
  </c:if>
  <form action="<c:url value='/security_check.html'/>" method="post" class="form-horizontal">
    <div class="control-group">
      <label class="control-label" for="j_username">Email</label>
      <div class="controls">
        <input type="text" id="j_username" name="j_username" placeholder="Email"
          value="${fn:escapeXml(sessionScope.SPRING_SECURITY_LAST_USERNAME)}"/>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="j_password">Password</label>
      <div class="controls">
        <input type="password" id="j_password" name="j_password" placeholder="Password"/>
      </div>
    </div>
    <div class="control-group">
      <div class="controls">
        <label class="checkbox">
          <input type="checkbox" name="_spring_security_remember_me"/> Remember me
        </label>
        <button type="submit" class="btn btn-primary">Sign in</button>
      </div>
    </div>
  </form>
</tags:page>
