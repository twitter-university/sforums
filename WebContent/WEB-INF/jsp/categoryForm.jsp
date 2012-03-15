<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
  <head>
    <title>Category Form</title>
    <jsp:include page="htmlHead.jsp"/>
  </head>
  <body>
    <jsp:include page="navBar.jsp"/>
    <h1>Category Form</h1>
    <form:form commandName="category">
      <table>
        <c:if test="${category.idSet}">
          <tr>
            <th>ID:</th>
            <td>${category.id}</td>
          </tr>
        </c:if>
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