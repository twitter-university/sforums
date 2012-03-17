<%@ tag body-content="scriptless"%>
<%@ attribute name="forums" type="java.util.Collection" required="true" rtexprvalue="true" %>
<%@ attribute name="showCategoryName" type="java.lang.Boolean" required="false" rtexprvalue="true" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:choose>
  <c:when test="${fn:length(forums) == 0}">
    <p>No forums</p>
  </c:when>
  <c:otherwise>
    <table>
      <thead>
        <tr>
          <th>Forum</th>
          <th>Description</th>
          <th>Topics</th>
          <security:authorize ifAllGranted="ROLE_ADMIN">
            <th><em>Actions</em></th>
          </security:authorize>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="forum" items="${forums}">
          <tr>
            <td>
              <a href="<c:url value='/forum.html?id=${forum.id}'/>">
                <c:if test="${showCategoryName}">${forum.category.name} &raquo;</c:if>
                ${forum.name}
              </a>
            </td>
            <td>${f:trimToLength(forum.description, 50)}</td>
            <td class="number">${fn:length(forum.topics)}</td>
            <security:authorize ifAllGranted="ROLE_ADMIN">
              <td class="small">
                <a class="button" href="<c:url value='/forum_form.html?id=${forum.id}'/>">Edit</a>
                <a class="button" href="<c:url value='/forum_delete.html?id=${forum.id}'/>"
                  onclick="return confirm('Are you sure you wish to delete this forum?');">Delete</a>
              </td>
            </security:authorize>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </c:otherwise>
</c:choose>