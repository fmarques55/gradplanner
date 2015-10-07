
<%@ page import="gradplanner.TranscriptOfRecords" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'transcriptOfRecords.label', default: 'TranscriptOfRecords')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-transcriptOfRecords" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-transcriptOfRecords" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list transcriptOfRecords">
			
				<g:if test="${transcriptOfRecordsInstance?.ira}">
				<li class="fieldcontain">
					<span id="ira-label" class="property-label"><g:message code="transcriptOfRecords.ira.label" default="Ira" /></span>
					
						<span class="property-value" aria-labelledby="ira-label"><g:fieldValue bean="${transcriptOfRecordsInstance}" field="ira"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${transcriptOfRecordsInstance?.results}">
				<li class="fieldcontain">
					<span id="results-label" class="property-label"><g:message code="transcriptOfRecords.results.label" default="Results" /></span>
					
						<g:each in="${transcriptOfRecordsInstance.results}" var="r">
						<span class="property-value" aria-labelledby="results-label"><g:link controller="result" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${transcriptOfRecordsInstance?.student}">
				<li class="fieldcontain">
					<span id="student-label" class="property-label"><g:message code="transcriptOfRecords.student.label" default="Student" /></span>
					
						<span class="property-value" aria-labelledby="student-label"><g:link controller="student" action="show" id="${transcriptOfRecordsInstance?.student?.id}">${transcriptOfRecordsInstance?.student?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${transcriptOfRecordsInstance?.id}" />
					<g:link class="edit" action="edit" id="${transcriptOfRecordsInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
