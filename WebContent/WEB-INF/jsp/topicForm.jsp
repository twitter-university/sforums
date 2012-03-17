<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
  <head>
    <title>Topic Form</title>
    <jsp:include page="htmlHead.jsp"/>
  </head>
  <body>
    <jsp:include page="navBar.jsp"/>
    <h1>Topic Form</h1>
    <form:form commandName="topic">
      <table>
        <spring:hasBindErrors name="topic">
          <tr>
            <th></th>
            <td class="error">
              Form Errors<br/>
              <form:errors/>
            </td>
          </tr>
        </spring:hasBindErrors>
        <c:if test="${topic.idSet}">
          <tr>
            <th>ID:</th>
            <td>${topic.id}</td>
          </tr>
        </c:if>
        <tr>
          <th><form:label path="forum">Forum</form:label> *:</th>
          <td>
            <form:select path="forum">
              <form:option value="">--- Please Select ---</form:option>
              <form:options items="${forumList}" itemValue="id" itemLabel="fullName"/>
            </form:select>
            <form:errors path="forum" cssClass="error"/>
          </td>
        </tr>
        <tr>
          <th><form:label path="title">Title</form:label> *:</th>
          <td>
            <form:input path="title" />
            <form:errors path="title" cssClass="error"/>
          </td>
        </tr>
        <tr>
          <th><form:label path="message">Message</form:label> *:</th>
          <td>
            <form:textarea path="message" cols="80" rows="8" />
            <form:errors path="message" cssClass="error"/>
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