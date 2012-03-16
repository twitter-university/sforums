<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${authUser != null}">
  <div class="authUserGreeting">
    Welcome ${authUser}!
  </div>
</c:if>
<div class="navBar">
  <a class="button" href="<c:url value='/home.html'/>">Home</a>
  <security:authorize ifAllGranted="ROLE_ADMIN">
    <a class="button" href="<c:url value='/category_form.html'/>">Add Category</a>
  </security:authorize>
  <a class="button" href="<c:url value='/categories.html'/>">List Categories</a>
  <security:authorize ifAllGranted="ROLE_ADMIN">
    <a class="button" href="<c:url value='/user_form.html'/>">Add User</a>
  </security:authorize>
  <a class="button" href="<c:url value='/users.html'/>">List Users</a>
  <security:authorize ifAllGranted="ROLE_USER">
    <a class="button" href="<c:url value='/user_profile.html'/>">My Profile</a>
    <a class="button" href="<c:url value='/logout.html'/>">Logout</a>
  </security:authorize>
  <security:authorize ifNotGranted="ROLE_USER">
    <a class="button" href="<c:url value='/login.html'/>">Login</a>
    <a class="button" href="<c:url value='/user_register.html'/>">Register</a>
  </security:authorize>
</div>