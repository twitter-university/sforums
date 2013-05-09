<%@ tag body-content="scriptless"%>
<%@ attribute name="name" required="true" rtexprvalue="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<spring:hasBindErrors name="${name}">
  <tags:alert type="error" title="Form Errors">
    <form:errors />
  </tags:alert>
</spring:hasBindErrors>