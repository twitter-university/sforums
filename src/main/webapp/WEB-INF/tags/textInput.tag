<%@ tag body-content="scriptless"%>
<%@ attribute name="label" required="false" rtexprvalue="true"%>
<%@ attribute name="name" required="true" rtexprvalue="true"%>
<%@ attribute name="value" required="false" rtexprvalue="true"%>
<%@ attribute name="type" required="false" rtexprvalue="true"%>
<%@ attribute name="placeholder" required="false" rtexprvalue="true"%>
<%@ attribute name="required" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true"%>
<%@ attribute name="autocomplete" required="false" rtexprvalue="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>

<div class="control-group ${pageScope.required? 'required' : ''}">
  <c:if test="${not empty pageScope.label}">
    <label for="${pageScope.name}" class="control-label">${pageScope.label}</label>
  </c:if>
  <div class="controls">
    <input id="${pageScope.name}" name="${pageScope.name}"
      type="${empty pageScope.type? 'text' : pageScope.type}"
      placeholder="${pageScope.placeholder}"
      class="${empty pageScope.cssClass? 'input-xlarge' : pageScope.cssClass}"
      value="${fn:escapeXml(pageScope.value)}"
      autocomplete="${pageScope.autocomplete}"
      ${pageScope.required? 'required' : ''} />
  </div>
</div>