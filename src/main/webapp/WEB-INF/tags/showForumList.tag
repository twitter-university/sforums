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
    <table class="table table-striped table-hover">
      <thead>
        <tr>
          <th class="span${showCategoryName? 4 : 3}">Forum</th>
          <th>Description</th>
          <th class="number span1">Topics</th>
          <security:authorize ifAllGranted="ROLE_ADMIN">
            <th class="span1"></th>
          </security:authorize>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="forum" items="${forums}">
          <c:url var="viewUrl" value="/forum.html">
            <c:param name="id" value="${forum.id}"/>
          </c:url>
          <tr class="deleteContainer">
            <td class="span${showCategoryName? 4 : 3}">
              <a href="${viewUrl}">
                <c:if test="${showCategoryName}">${fn:escapeXml(forum.category.name)} &raquo;</c:if>
                ${fn:escapeXml(forum.name)}
              </a>
            </td>
            <td>${fn:escapeXml(f:trimToLength(forum.description, 100))}</td>
            <td class="number span1">${fn:length(forum.topics)}</td>
            <security:authorize ifAllGranted="ROLE_ADMIN">
              <c:url var="editUrl" value="/forum_form.html">
                <c:param name="id" value="${forum.id}"/>
              </c:url>
              <c:url var="deleteUrl" value="/forum_delete.html">
                <c:param name="id" value="${forum.id}"/>
              </c:url>
              <td class="span1">
                <a class="editUrl" href="${editUrl}"><i class="icon-pencil"></i></a>
                <a class="forum-deleteUrl" href="${deleteUrl}"><i class="icon-trash"></i></a>
              </td>
            </security:authorize>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </c:otherwise>
</c:choose>
<security:authorize ifAllGranted="ROLE_ADMIN">
  <script type="text/javascript">
    $(document).ready(function() {
      executeDeleteAndRemoveContainer(".forum-deleteUrl", ".deleteContainer");
    });
  </script>
</security:authorize>