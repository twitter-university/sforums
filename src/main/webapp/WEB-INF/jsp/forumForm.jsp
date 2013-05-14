<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<tags:page title="Forum Form" nav="add_forum">
  <c:if test="${not empty param.success}">
    <tags:alert type="success" title="Success!" message="Saved forum."/>
  </c:if>
  <form:form action="forum_form.html" commandName="forum" cssClass="form-horizontal">
    <tags:showFormErrors name="forum"/>
    <tags:formInput path="category" label="Category" required="${true}">
      <form:select path="category">
        <form:option value="">--- Please Select ---</form:option>
        <form:options items="${categoryList}" itemValue="id" itemLabel="name"/>
      </form:select>
    </tags:formInput>
    <tags:textInput path="name" label="Name" required="${true}"/>
    <tags:textAreaInput path="description" label="Description" required="${false}" rows="5"/>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary">Save</button>
    </div>
  </form:form>
</tags:page>