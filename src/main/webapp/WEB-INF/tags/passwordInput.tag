<%@ tag body-content="scriptless"%>
<%@ attribute name="label" required="false" rtexprvalue="true"%>
<%@ attribute name="path" required="true" rtexprvalue="true"%>
<%@ attribute name="required" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true"%>
<%@ attribute name="autocomplete" required="false" rtexprvalue="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>

<tags:formInput path="${pageScope.path}" label="${pageScope.label}" required="${pageScope.required}">
  <form:password path="${pageScope.path}"
    cssClass="${empty pageScope.cssClass? 'input-xlarge' : pageScope.cssClass}"
    autocomplete="${pageScope.autocomplete}" />
</tags:formInput>