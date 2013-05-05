<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<tags:page title="User Form" nav="add_user">
  <c:choose>
    <c:when test="${fn:length(errors) != 0}">
      <c:forEach var="error" items="${errors}">
        <tags:alert type="error" title="Error!" message="${error}"/>
      </c:forEach>
    </c:when>
    <c:when test="${success}">
      <tags:alert type="success" title="Success!" message="Saved user."/>
    </c:when>
  </c:choose>
  <form method="post" action="<c:url value='/user_save.html'/>" class="form-horizontal">
    <tags:textInput name="firstName" label="First Name" value="${user.firstName}" required="${false}"/>
    <tags:textInput name="lastName" label="Last Name" value="${user.lastName}" required="${false}"/>
    <tags:textInput name="title" label="Title" value="${user.title}"/>
    <tags:textInput name="organization" label="Organization" value="${user.organization}"/>
    <tags:textInput name="email" label="Email" value="${user.email}" type="email" required="${false}" autocomplete="off"/>
    <tags:textInput name="password" label="Password" type="password" autocomplete="off"/>
    <tags:textInput name="passwordVerification" label="Password Verification" type="password" autocomplete="off"/>
    <div class="form-actions">
      <input type="hidden" name="id" value="${user.id}"/>
      <button type="submit" class="btn btn-primary">Save</button>
    </div>
  </form>
</tags:page>