<%@ tag body-content="scriptless"%>
<%@ attribute name="post" type="sforums.domain.Post" required="true" rtexprvalue="true" %>
<%@ attribute name="postType" type="java.lang.String" required="false" rtexprvalue="true" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<div class="${postType}" id="${postType}${post.id}">
  <div class="post">
    <div class="header">
      <div class="author">${post.author}</div>
      <div class="date"><fmt:formatDate value="${post.created}" type="BOTH"/></div>
      <br style="clear: both"/>
      <security:authorize ifAllGranted="ROLE_ADMIN">
        <div class="actions">
          <c:if test="${postType eq 'topic'}">
            <a class="button" href="<c:url value='/${postType}_form.html?id=${post.id}'/>">Edit</a>
          </c:if>
          <a class="button" href="<c:url value='/${postType}_delete.html?id=${post.id}'/>"
            onclick="return confirm('Are you sure you wish to delete this ${postType}?');">Delete</a>
        </div>
      </security:authorize>
    </div>
    <div class="message">${f:convertToHtmlLineBreaks(post.message)}</div>
  </div>
</div>