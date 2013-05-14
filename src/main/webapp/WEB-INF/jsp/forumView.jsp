<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="f" uri="/WEB-INF/functions.tld"%>
<tags:page title="${forum.name}" nav="forums">
  <ul class="breadcrumb">
    <c:url var="categoryUrl" value="category.html">
      <c:param name="id" value="${forum.category.id}"/>
    </c:url>
    <li><a href="${categoryUrl}">${fn:escapeXml(forum.category.name)}</a> <span class="divider">&raquo;</span></li>
    <li class="active">${fn:escapeXml(forum.name)}</li>
  </ul>
  <div class="pull-right">
    <security:authorize ifAllGranted="ROLE_USER">
      <c:url var="postUrl" value="/topic_form.html">
        <c:param name="forumId" value="${forum.id}" />
      </c:url>
      <a class="btn btn-success" href="${postUrl}">Post</a>
    </security:authorize>
    <security:authorize ifAllGranted="ROLE_ADMIN">
      <c:url var="editUrl" value="/forum_form.html">
        <c:param name="id" value="${forum.id}" />
      </c:url>
      <c:url var="deleteUrl" value="/forum_delete.html">
        <c:param name="id" value="${forum.id}" />
      </c:url>
      <c:url var="forumsUrl" value="/forums.html"/>
      <a class="editUrl btn btn-primary" href="${editUrl}">Edit</a>
      <a class="deleteUrl btn btn-danger" href="${deleteUrl}">Delete</a>
    </security:authorize>
  </div>
  
  <p>${f:convertToHtmlLineBreaks(forum.description)}</p>

  <c:choose>
    <c:when test="${fn:length(forum.topics) == 0}">
      <p>No topics</p>
    </c:when>
    <c:otherwise>
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th class="span4">Title</th>
            <th>Message</th>
            <th class="span3">Author</th>
            <th class="span1">Posted</th>
            <th class="span1 number">Replies</th>
            <security:authorize ifAllGranted="ROLE_ADMIN">
              <th class="span1"></th>
            </security:authorize>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${forum.topics}" var="topic">
            <c:url var="viewUrl" value="/topic.html">
              <c:param name="id" value="${topic.id}"/>
            </c:url>
            <tr class="deleteContainer">
              <td class="span4"><a href="${viewUrl}">${fn:escapeXml(topic.title)}</a></td>
              <td>${fn:escapeXml(f:trimToLength(topic.message, 40))}</td>
              <td>${fn:escapeXml(topic.author.name)}</td>
              <td class="span1"><fmt:formatDate value="${topic.created}" type="DATE" dateStyle="short"/></td>
              <td class="span1 number">${fn:length(topic.replies)}</td>
              <security:authorize ifAllGranted="ROLE_ADMIN">
                <c:url var="editUrl" value="/topic_form.html">
                  <c:param name="id" value="${topic.id}" />
                </c:url>
                <c:url var="deleteUrl" value="/topic_delete.html">
                  <c:param name="id" value="${topic.id}" />
                </c:url>                
                <td class="span1">
                  <a class="editUrl" href="${editUrl}"><i class="icon-pencil"></i></a>
                  <a class="topic-deleteUrl" href="${deleteUrl}"><i class="icon-trash"></i></a>
                </td>
              </security:authorize>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:otherwise>
  </c:choose>

  <security:authorize ifAllGranted="ROLE_ADMIN">
    <script type="text/javascript">
      $(document).ready(function() {
    	executeDeleteAndRedirect(".deleteUrl", "${forumsUrl}");
        executeDeleteAndRemoveContainer(".topic-deleteUrl", ".deleteContainer");
      });
    </script>
  </security:authorize>
</tags:page>