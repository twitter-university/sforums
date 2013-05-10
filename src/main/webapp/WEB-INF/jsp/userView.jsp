<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<tags:page title="${fn:escapeXml(user.name)}" nav="users">
  <c:if test="${not empty user.title or not empty user.organization}">
    <p>
      <c:if test="${not empty user.title}">
        ${fn:escapeXml(user.title)}<c:if test="${not empty user.organization}">,</c:if>
      </c:if>
      <c:if test="${not empty user.organization}">
        ${fn:escapeXml(user.organization)}
      </c:if>
    </p>
  </c:if>
  <c:if test="${not empty user.email}">
    <c:set var="email" value="${fn:escapeXml(user.email)}"/>
    <p><a href="mailto:${email}">${email}</a></p>
  </c:if>
  <p>Since <fmt:formatDate value="${user.created}" pattern="MMMMM d, yyyy hh:mma" /></p>
  <security:authorize ifAllGranted="ROLE_ADMIN">
    <c:url var="editUrl" value="/user_form.html">
      <c:param name="id" value="${user.id}" />
    </c:url>
    <c:url var="deleteUrl" value="/user_delete.html">
      <c:param name="id" value="${user.id}" />
    </c:url>
    <c:url var="usersUrl" value="/users.html"/>
    <p>
      <a class="editUrl btn btn-primary" href="${editUrl}">Edit</a>
      <a class="deleteUrl btn btn-danger" href="${deleteUrl}">Delete</a>
    </p>
    <script type="text/javascript">
      $(document).ready(function() {
          executeDeleteAndRedirect(".deleteUrl", "${usersUrl}");
      });
    </script>
  </security:authorize>
</tags:page>