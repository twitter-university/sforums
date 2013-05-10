<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<tags:page title="${category.name}" nav="categories">
  <p>${f:convertToHtmlLineBreaks(category.description)}</p>
  <security:authorize ifAllGranted="ROLE_ADMIN">
    <c:url var="editUrl" value="/category_form.html">
      <c:param name="id" value="${category.id}" />
    </c:url>
    <c:url var="deleteUrl" value="/category_delete.html">
      <c:param name="id" value="${category.id}" />
    </c:url>
    <c:url var="categoriesUrl" value="/categories.html"/>
    <p>
      <a class="editUrl btn btn-primary" href="${editUrl}">Edit</a>
      <a class="deleteUrl btn btn-danger" href="${deleteUrl}">Delete</a>
    </p>
    <script type="text/javascript">
      $(document).ready(function() {
      	executeDeleteAndRedirect(".deleteUrl", "${categoriesUrl}");
      });
    </script>
  </security:authorize>
</tags:page>