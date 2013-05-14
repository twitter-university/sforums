<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<tags:page title="User Form" nav="${requestScope.profile? 'user_profile' : 'add_user'}">
  <c:if test="${not empty param.success}">
    <tags:alert type="success" title="Success!" message="Saved user."/>
  </c:if>
  <form:form commandName="userAndPassword" cssClass="form-horizontal">
    <tags:showFormErrors name="userAndPassword"/>
    <spring:nestedPath path="user">
      <spring:nestedPath path="name">
        <tags:textInput path="first" label="First Name" required="${true}"/>
        <tags:textInput path="last" label="Last Name" required="${true}"/>
      </spring:nestedPath>
      <tags:textInput path="title" label="Title" />
      <tags:textInput path="organization" label="Organization" />
      <tags:textInput path="email" label="Email" required="${true}" autocomplete="off"/>
      <c:if test="${not requestScope.profile}">
        <tags:booleanInput path="enabled" label="Enabled"/>
        <tags:booleanInput path="admin" label="Admin"/>
      </c:if>
    </spring:nestedPath>
    <c:set var="passwordRequired" value="${not userAndPassword.user.idSet}"/>
    <tags:passwordInput path="password" label="Password" autocomplete="off" 
      required="${passwordRequired}"/>
    <tags:passwordInput path="passwordVerification" label="Password Verification" autocomplete="off"
      required="${passwordRequired}"/>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary">Save</button>
    </div>
  </form:form>
</tags:page>