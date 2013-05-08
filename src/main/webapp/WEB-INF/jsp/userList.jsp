<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<tags:page title="Users" nav="users">
  <c:choose>
    <c:when test="${fn:length(userList) == 0}">
      <p>No users</p>
    </c:when>
    <c:otherwise>
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th class="number">Id</th>
            <th>Name</th>
            <th>Title</th>
            <th>Organization</th>
            <th>Email</th>
            <th>Since</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="user" items="${userList}">
            <c:url var="viewUrl" value="/user.html">
              <c:param name="id" value="${user.id}"/>
            </c:url>
            <c:url var="editUrl" value="/user_form.html">
              <c:param name="id" value="${user.id}"/>
            </c:url>
            <c:url var="deleteUrl" value="/user_delete.html">
              <c:param name="id" value="${user.id}"/>
            </c:url>
           <tr>
              <td class="number">${user.id}</td>
              <td><a href="${viewUrl}">${fn:escapeXml(user.name)}</a></td>
              <td>${fn:escapeXml(f:trimToLength(user.title, 30))}</td>
              <td>${fn:escapeXml(f:trimToLength(user.organization, 30))}</td>
              <td>
                <a href="mailto:${fn:escapeXml(user.email)}">
                  ${fn:escapeXml(f:trimToLength(user.email, 30))}
                </a>
              </td>
              <td>
                <fmt:formatDate value="${user.created}" pattern="MMM yyyy" />
              </td>
              <td>
                <a class="editUrl" href="${editUrl}"><i class="icon-pencil"></i></a>
                <a class="deleteUrl" href="${deleteUrl}"><i class="icon-trash"></i></a>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:otherwise>
  </c:choose>
  <script type="text/javascript">
    $(document).ready(function() {
      executeDeleteAndRemoveContainer(".deleteUrl", "tr");
    });
  </script>
</tags:page>