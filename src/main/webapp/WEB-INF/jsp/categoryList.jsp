<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<tags:page title="Categories" nav="categories">
  <c:choose>
    <c:when test="${fn:length(categoryList) == 0}">
      <p>No categories</p>
    </c:when>
    <c:otherwise>
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th class="number">Id</th>
            <th>Name</th>
            <th>Description</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${categoryList}" var="category">
            <c:url var="viewUrl" value="/category.html">
              <c:param name="id" value="${category.id}"/>
            </c:url>
            <c:url var="editUrl" value="/category_form.html">
              <c:param name="id" value="${category.id}"/>
            </c:url>
            <c:url var="deleteUrl" value="/category_delete.html">
              <c:param name="id" value="${category.id}"/>
            </c:url>
            <tr>
              <td class="number">${category.id}</td>
              <td>
                <a href="${viewUrl}">${fn:escapeXml(category.name)}</a>
              </td>
              <td>${f:trimToLength(category.description, 40)}</td>
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