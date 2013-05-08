<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<tags:page title="User Form" nav="add_user">
  <c:if test="${not empty param.success}">
    <tags:alert type="success" title="Success!" message="Saved user."/>
  </c:if>
  <form:form commandName="user" cssClass="form-horizontal">
    <tags:textInput path="firstName" label="First Name" required="${false}"/>
    <tags:textInput path="lastName" label="Last Name" required="${false}"/>
    <tags:textInput path="title" label="Title" />
    <tags:textInput path="organization" label="Organization" />
    <tags:textInput path="email" label="Email" required="${false}" autocomplete="off"/>
    <tags:passwordInput path="password" label="Password" autocomplete="off"/>
    <tags:passwordInput path="passwordVerification" label="Password Verification" autocomplete="off"/>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary">Save</button>
    </div>
  </form:form>
</tags:page>