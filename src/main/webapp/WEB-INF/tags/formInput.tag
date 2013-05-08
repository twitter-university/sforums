<%@ tag body-content="scriptless" %>
<%@ attribute name="label" required="false" rtexprvalue="true"%>
<%@ attribute name="path" required="true" rtexprvalue="true"%>
<%@ attribute name="required" required="false" rtexprvalue="true" type="java.lang.Boolean"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>

<c:set var="error">
  <form:errors path="${pageScope.path}" cssClass="help-inline" />
</c:set>
<div class="control-group ${pageScope.required? 'required' : ''} ${empty pageScope.error? '' : 'error'}">
  <c:if test="${not empty pageScope.label}">
    <form:label path="${pageScope.path}" cssClass="control-label">${pageScope.label}</form:label>
  </c:if>
  <div class="controls">
    <jsp:doBody/>
    ${pageScope.error}
  </div>
</div>