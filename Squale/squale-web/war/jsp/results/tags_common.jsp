<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"	prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>

<script>
function find_parental_form_element(el)
{
      for (var i=0; i < document.forms.length; i++)
      {
            var d = document.forms[i];
            
            for (var x=0; x < d.elements.length; x++)
            {
                  if (d.elements[x] == el)
                  {
                        return d;
                  }
            }
      }      
}            

function tagExecSubmit(currentForm,action,me)
{
    currentForm.action.value = action;
    if ((me==null || me==window || me.type=='button') && currentForm.onsubmit()){
	currentForm.submit();
    } else {
    }
}
</script>

<bean:define id="applicationId" name='<%=(String) request.getAttribute("formName")%>'
	property="applicationId" type="String" />
<bean:define id="projectId" name='<%=(String) request.getAttribute("formName")%>' property="projectId"
	type="String" />
<bean:define id="callbackUrlApp">
	<html:rewrite page='<%="/add_applicationTag.do?action=findTagForAutocomplete&applicationId=" + applicationId%>' />
</bean:define>
<bean:define id="callbackUrlProj">
	<html:rewrite page='<%="/add_projectTag.do?action=findTagForAutocomplete&applicationId=" + applicationId%>' />
</bean:define>	
<bean:define id="currentAuditId" name='<%=(String) request.getAttribute("formName")%>'
	property="currentAuditId" type="String" />
	
<logic:empty name='<%=(String) request.getAttribute("formName")%>' property="projectId">
	<div id="tagRemoval">
		<af:form action="application.do">
			<div id="appTagRemoval" style="visibility:hidden">
				<div id="hidden" style="display:none;">
					<af:field key="empty" property="applicationId" value='<%= applicationId%>'/>
				</div>
				<table>
					<tr>
						<td>
							<af:select property="tagDel">
								<af:options collection="tagsApplication" property="name"/>
							</af:select>
						</td>
						<td>
							<af:button type="form" onclick="tagExecSubmit(find_parental_form_element(this),'removeTag',this)" name="supprimer" singleSend="true"/>
						</td>
					</tr>
				</table>
			</div>
		</af:form>
	</div>
	<div id="tagAddition">
		<div id="appTagAddition" style="visibility:hidden">
			<af:form action='<%="application.do?action=addTag&applicationId=" + applicationId + "&currentAuditId=" + currentAuditId%>'>
				<af:field key="empty" property="tagSupp" value="" easyCompleteCallBackUrl="<%=callbackUrlApp%>"/>
			</af:form>
		</div>
	</div>
</logic:empty>

<logic:notEmpty name='<%=(String) request.getAttribute("formName")%>' property="projectId">
	<div id="tagRemoval">
		<af:form action="project.do">
			<div id="appTagRemoval" style="visibility: hidden">
				<div id="hidden" style="display: none;">
					<af:field key="empty" property="applicationId" value='<%= applicationId%>' /> 
					<af:field key="empty" property="projectId" value='<%= projectId%>' />
				</div>
				<table>
					<tr>
						<td>
							<af:select property="tagDelAppli">
								<af:options collection="tagsApplication" property="name" />
							</af:select>
						</td>
						<td>
							<af:button type="form" onclick="tagExecSubmit(find_parental_form_element(this),'removeTagApplication',this)" name="supprimer" singleSend="true" />
						</td>
					</tr>
				</table>
			</div>
			<div id="projTagRemoval" style="visibility: hidden">
				<div id="hidden" style="display: none;">
					<af:field key="empty" property="applicationId" value='<%= applicationId%>' /> 
					<af:field key="empty" property="projectId" value='<%= projectId%>' />
				</div>
				<table>
					<tr>
						<td>
							<af:select property="tagDel">
								<af:options collection="tagsProjet" property="name" />
							</af:select>
						</td>
						<td>
							<af:button type="form" onclick="tagExecSubmit(find_parental_form_element(this),'removeTag',this)" name="supprimer" singleSend="true" />
						</td>
					</tr>
				</table>
			</div>
		</af:form>
	</div>
	<div id="tagAddition">
		<div id="appTagAddition" style="visibility: hidden">
			<af:form action='<%="project.do?action=addTagApplication&applicationId=" + applicationId + "&currentAuditId=" + currentAuditId + "&projectId=" + projectId%>'>
				<af:field key="empty" property="tagSupp" value="" easyCompleteCallBackUrl="<%=callbackUrlApp%>" />
			</af:form>
		</div>
		<div id="projTagAddition" style="visibility: hidden">
			<af:form action='<%="project.do?action=addTag&projectId=" + projectId + "&currentAuditId=" + currentAuditId%>'>
				<af:field key="empty" property="tagSupp" value="" easyCompleteCallBackUrl="<%=callbackUrlProj%>" />
			</af:form>
		</div>
	</div>
</logic:notEmpty>


<%-- FINISH THIS UP ! https://project.squale.org/ticket/140
<logic:present name="unexistingTag" scope="request">
	<div id="unexistingTagBox" title="Tag does not exist">
	<bean:write name="unexistingTag"/>
	</div>
	<script>
	showErrorModalBox("unexistingTagBox");
	</script>
</logic:present>
--%>