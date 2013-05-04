<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<tags:page title="Category Form" nav="add_category">
  <c:choose>
    <c:when test="${not empty error}">
      <tags:alert type="error" title="Error!" message="${error}"/>
    </c:when>
    <c:when test="${success}">
      <tags:alert type="success" title="Success!" message="Saved category."/>
    </c:when>
  </c:choose>
  <form method="post" action="<c:url value='/category_save.html'/>" class="form-horizontal">
    <div class="control-group required">
      <label for="name" class="control-label">Name</label>
      <div class="controls">
        <input id="name" type="text" name="name" placeholder="Category Name" class="input-xlarge"
          value="${fn:escapeXml(category.name)}" required/>
      </div>
    </div>
    <div class="control-group">
      <label for="description" class="control-label">Description</label>
      <div class="controls">
       <textarea id="description" name="description" rows="5" class="input-xlarge"
         placeholder="Category Description">${fn:escapeXml(category.description)}</textarea>
      </div>
    </div>
    <div class="form-actions">
      <input type="hidden" name="id" value="${category.id}"/>
      <button type="submit" class="btn btn-primary">Save</button>
    </div>
  </form>
</tags:page>
