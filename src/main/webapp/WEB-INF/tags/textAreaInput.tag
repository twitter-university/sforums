<%@ tag body-content="scriptless"%>
<%@ attribute name="label" required="false" rtexprvalue="true"%>
<%@ attribute name="path" required="true" rtexprvalue="true"%>
<%@ attribute name="required" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true"%>
<%@ attribute name="rows" required="false" rtexprvalue="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>

<tags:formInput path="${pageScope.path}" label="${pageScope.label}" required="${pageScope.required}">
  <form:textarea path="${pageScope.path}" rows="${empty pageScope.rows? 6 : pageScope.rows}"
    cssClass="${empty pageScope.cssClass? 'input-xlarge' : pageScope.cssClass}" />
</tags:formInput>