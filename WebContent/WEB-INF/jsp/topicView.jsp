<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
  <head>
    <title>${topic.title}</title>
    <jsp:include page="htmlHead.jsp"/>
  </head>
  <body>
    <jsp:include page="navBar.jsp"/>
    <h1>${topic.forum.category.name} &raquo; ${topic.forum.name} &raquo; ${topic.title}</h1>
    <div class="breadcrumbs">
      <a href="<c:url value='/category.html?id=${topic.forum.category.id}'/>">${topic.forum.category.name}</a>
      &raquo;
      <a href="<c:url value='/forum.html?id=${topic.forum.id}'/>">${topic.forum.name}</a>
    </div>
    <tags:postView post="${topic}" postType="topic"/>
    <c:forEach var="reply" items="${topic.replies}">
      <tags:postView post="${reply}" postType="reply"/>
    </c:forEach>
    <security:authorize ifAllGranted="ROLE_USER">
      <form action="<c:url value='/reply_form.html'/>" method="post">
        <div class="post">
          <div class="header">
            <div class="author">${authUser}</div>
            <div class="date"><fmt:formatDate value="${f:now()}" type="BOTH"/></div>
            <br style="clear: both"/>
          </div>
          <div class="message">
            <textarea name="message" rows="8"></textarea>
            <br/>
            <input type="hidden" name="topic" value="${topic.id}"/>
            <input type="submit" value="Post Reply"/>
          </div>
        </div>
      </form>
    </security:authorize>
  </body>
</html>