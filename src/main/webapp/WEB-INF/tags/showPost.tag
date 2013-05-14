<%@ tag body-content="scriptless"%>
<%@ attribute name="post" type="com.marakana.sforums.domain.Post" required="true" rtexprvalue="true" %>
<%@ attribute name="postType" type="java.lang.String" required="false" rtexprvalue="true" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<div class="${postType} deleteContainer" id="p${post.id}">
  <div class="post">
    <div class="header">
      <div class="author">${post.author}</div>
      <div class="dateTime"><fmt:formatDate value="${post.created}" type="BOTH"/></div>
      <br style="clear: both"/>
    </div>
    <div class="message">
      <security:authorize ifAllGranted="ROLE_ADMIN">
        <span class="pull-right">
          <c:if test="${postType eq 'topic'}">
            <c:url var="editUrl" value="/${postType}_form.html">
              <c:param name="id" value="${post.id}" />
            </c:url>
            <a class="editUrl" href="${editUrl}"><i class="icon-pencil"></i></a>
          </c:if>
          <c:url var="deleteUrl" value="/${postType}_delete.html">
            <c:param name="id" value="${post.id}" />
          </c:url>
          <a class="${postType}-deleteUrl" href="${deleteUrl}"><i class="icon-trash"></i></a>
        </span>
      </security:authorize>
      ${f:convertToHtmlLineBreaks(post.message)}
    </div>
  </div>
</div>
