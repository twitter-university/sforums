<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<tags:page title="${fn:escapeXml(topic.title)}">
  <c:url var="categoryUrl" value="category.html">
    <c:param name="id" value="${topic.forum.category.id}"/>
  </c:url>
  <c:url var="forumUrl" value="forum.html">
    <c:param name="id" value="${topic.forum.id}"/>
  </c:url>
  <ul class="breadcrumb">
    <li><a href="${categoryUrl}">${fn:escapeXml(topic.forum.category.name)}</a> <span class="divider">&raquo;</span></li>
    <li><a href="${forumUrl}">${fn:escapeXml(topic.forum.name)}</a> <span class="divider">&raquo;</span></li>
    <li class="active">${fn:escapeXml(topic.title)}</li>
  </ul>
  <c:if test="${not empty param.success}">
    <tags:alert type="success" title="Success!" message="Posted reply."/>
  </c:if>
  <tags:showPost post="${topic}" postType="topic"/>
  <c:forEach var="reply" items="${topic.replies}">
    <tags:showPost post="${reply}" postType="reply"/>
  </c:forEach>
  <security:authorize ifAllGranted="ROLE_USER">
    <form action="<c:url value='/reply_form.html'/>" method="post" class="form-horizontal">
      <div class="post">
        <div class="header">
          <div class="author">${authUser}</div>
          <div class="dateTime"><fmt:formatDate value="${f:now()}" type="BOTH"/></div>
          <br style="clear: both"/>
        </div>
        <textarea class="message input-block-level" name="message" rows="8" placeholder="Your reply"></textarea>
        <div class="form-actions">
          <input type="hidden" name="topic" value="${topic.id}"/>
          <button type="submit" class="btn btn-success">Post Reply</button>
        </div>
      </div>
    </form>
  </security:authorize>
  <security:authorize ifAllGranted="ROLE_ADMIN">
    <script type="text/javascript">
      $(document).ready(function() {
        executeDeleteAndRedirect(".topic-deleteUrl", "${forumUrl}");
        executeDeleteAndRemoveContainer(".reply-deleteUrl", ".deleteContainer");
      });
    </script>
  </security:authorize>
</tags:page>