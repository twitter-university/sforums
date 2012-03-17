<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
  <head>
    <title>${forum.category.name} &raquo; ${forum.name} Forum</title>
    <jsp:include page="htmlHead.jsp"/>
  </head>
  <body>
    <jsp:include page="navBar.jsp"/>
    <h1>${forum.category.name} &raquo; ${forum.name} Forum</h1>
    <div class="breadcrumbs">
      <a href="<c:url value='/category.html?id=${forum.category.id}'/>">${forum.category.name}</a>
    </div>
    <p>${f:convertToHtmlLineBreaks(forum.description)}</p>
    <h2>Topics</h2>
    <c:choose>
      <c:when test="${fn:length(forum.topics) == 0}">
        <p>No topics</p>
      </c:when>
      <c:otherwise>
        <table>
          <thead>
            <tr>
              <th class="number">Id</th>
              <th>Title</th>
              <th>Message</th>
              <th>Author</th>
              <security:authorize ifAllGranted="ROLE_ADMIN">
                <th><em>Actions</em></th>
              </security:authorize>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${forum.topics}" var="topic">
               <tr>
                <td class="number">${topic.id}</td>
                <td>
                  <a href="<c:url value='/topic.html?id=${topic.id}'/>">
                    ${topic.title}
                  </a>
                </td>
                <td>${f:trimToLength(topic.message, 40)}</td>
                <td>${topic.author}</td>
                <security:authorize ifAllGranted="ROLE_ADMIN">
                  <td class="small">
                    <a class="button" href="<c:url value='/topic_form.html?id=${topic.id}'/>">Edit</a>
                    <a class="button" href="<c:url value='/topic_delete.html?id=${topic.id}'/>"
                      onclick="return confirm('Are you sure you wish to delete this topic?');">Delete</a>
                  </td>
                </security:authorize>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:otherwise>
    </c:choose>
    <p>
      <security:authorize ifAllGranted="ROLE_USER">
        <a class="button" href="<c:url value='/topic_form.html?forumId=${forum.id}'/>">New Topic</a>
      </security:authorize>
      <security:authorize ifAllGranted="ROLE_ADMIN">
        <a class="button" href="<c:url value='/forum_form.html?id=${forum.id}'/>">Edit</a>
        <a class="button" href="<c:url value='/forum_delete.html?id=${forum.id}'/>"
          onclick="return confirm('Are you sure you wish to delete this forum?');">Delete</a>
      </security:authorize>
    </p>
  </body>
</html>