<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
  <head>
    <title>Forum Form</title>
    <jsp:include page="htmlHead.jsp"/>
  </head>
  <body>
    <jsp:include page="navBar.jsp"/>
    <h1>Forum Form</h1>
    <form:form commandName="forum">
      <table>
        <spring:hasBindErrors name="forum">
          <tr>
            <th></th>
            <td class="error">
              Form Errors<br/>
              <form:errors/>
            </td>
          </tr>
        </spring:hasBindErrors>
        <c:if test="${forum.idSet}">
          <tr>
            <th>ID:</th>
            <td>${forum.id}</td>
          </tr>
        </c:if>
        <tr>
          <th><form:label path="category">Category</form:label> *:</th>
          <td>
            <form:select path="category">
              <form:option value="">--- Please Select ---</form:option>
              <form:options items="${categoryList}" itemValue="id" itemLabel="name"/>
            </form:select>
            <form:errors path="category" cssClass="error"/>
          </td>
        </tr>
        <tr>
          <th><form:label path="name">Name</form:label> *:</th>
          <td>
            <form:input path="name" />
            <form:errors path="name" cssClass="error"/>
          </td>
        </tr>
        <tr>
          <th><form:label path="description">Description</form:label>:</th>
          <td>
            <form:textarea path="description" cols="80" rows="8" />
            <form:errors path="description" cssClass="error"/>
          </td>
        </tr>
        <tr>
          <th>&nbsp;</th>
          <td>
            <input type="submit" value="Save"/>
          </td>
        </tr>
      </table>
    </form:form>
  </body>
</html>