<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<tags:page title="Topic Form">
  <c:if test="${not empty param.success}">
    <tags:alert type="success" title="Success!" message="Saved topic."/>
  </c:if>
  <form:form action="topic_form.html" commandName="topic" cssClass="form-horizontal">
    <tags:showFormErrors name="topic"/>
    <tags:formInput path="forum" label="Forum" required="${true}">
      <form:select path="forum">
        <form:option value="">--- Please Select ---</form:option>
        <form:options items="${forumList}" itemValue="id" itemLabel="categoryAndName"/>
      </form:select>
    </tags:formInput>
    <tags:textInput path="title" label="Title" required="${true}"/>
    <tags:textAreaInput path="message" label="Message" required="${false}" rows="5"/>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary">Save</button>
    </div>
  </form:form>
</tags:page>