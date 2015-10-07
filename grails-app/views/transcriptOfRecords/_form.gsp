<%@ page import="gradplanner.TranscriptOfRecords" %>



<div class="fieldcontain ${hasErrors(bean: transcriptOfRecordsInstance, field: 'ira', 'error')} required">
	<label for="ira">
		<g:message code="transcriptOfRecords.ira.label" default="Ira" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="ira" value="${fieldValue(bean: transcriptOfRecordsInstance, field: 'ira')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: transcriptOfRecordsInstance, field: 'results', 'error')} ">
	<label for="results">
		<g:message code="transcriptOfRecords.results.label" default="Results" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${transcriptOfRecordsInstance?.results?}" var="r">
    <li><g:link controller="result" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="result" action="create" params="['transcriptOfRecords.id': transcriptOfRecordsInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'result.label', default: 'Result')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: transcriptOfRecordsInstance, field: 'student', 'error')} required">
	<label for="student">
		<g:message code="transcriptOfRecords.student.label" default="Student" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="student" name="student.id" from="${gradplanner.Student.list()}" optionKey="id" required="" value="${transcriptOfRecordsInstance?.student?.id}" class="many-to-one"/>
</div>

