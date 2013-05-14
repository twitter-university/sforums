<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<tags:page title="Categories" nav="categories">
  <c:choose>
    <c:when test="${fn:length(categoryList) == 0}">
      <p>No categories</p>
    </c:when>
    <c:otherwise>
      <div class="row-fluid">
        <c:forEach items="${categoryList}" var="category" varStatus="categoryStatus">
          <c:url var="viewUrl" value="/category.html">
            <c:param name="id" value="${category.id}"/>
          </c:url>
          <c:url var="editUrl" value="/category_form.html">
            <c:param name="id" value="${category.id}"/>
          </c:url>
          <c:url var="deleteUrl" value="/category_delete.html">
            <c:param name="id" value="${category.id}"/>
          </c:url>
          <c:if test="${categoryStatus.index % 3 == 0}"></div><div class="row-fluid"></c:if>
          <div class="span4">
            <h2>${fn:escapeXml(category.name)}</h2>
            <p>${f:convertToHtmlLineBreaks(fn:escapeXml(category.description))}</p>
            <p>
              <small>
                <spring:message code="forumCountMessage" arguments="${fn:length(category.forums)}"/>
              </small>
            </p>
            <p>
              <a class="btn" href="${viewUrl}">View details &raquo;</a>
              <security:authorize ifAllGranted="ROLE_ADMIN">
                <a class="editUrl" href="${editUrl}"><i class="icon-pencil"></i></a>
                <a class="deleteUrl" href="${deleteUrl}"><i class="icon-trash"></i></a>
              </security:authorize>
            </p>
          </div>
        </c:forEach>
      </div>
    </c:otherwise>
  </c:choose>
  <security:authorize ifAllGranted="ROLE_ADMIN">
    <script type="text/javascript">
      $(document).ready(function() {
        executeDeleteAndRemoveContainer(".deleteUrl", "div.span4");
      });
    </script>
  </security:authorize>
</tags:page>