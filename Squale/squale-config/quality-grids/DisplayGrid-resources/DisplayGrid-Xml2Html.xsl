<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="squale">
	<html>
		<div id="gridBox">
			<xsl:apply-templates select="//grid"/>
		</div>
	</html>
</xsl:template>

<xsl:template match="grid">
	<h2>Grid ID: <xsl:value-of select="@name"/></h2>
	<xsl:for-each select="factor-ref">
		<xsl:apply-templates select="//factor[@name=current()/@name]"/>
	</xsl:for-each>
</xsl:template>

<xsl:template match="factor">
	<div class="factor">
		<div class="factorHeader" onclick="showHide(this);">
			<div class="factorName"> Factor <span class="factorName"><xsl:value-of select="@name"/></span></div>
		</div>
		<div>
			<xsl:for-each select="criterium-ref">
				<div class="criteria">
					<div class="criteriaHeader" onclick="showHide(this);">
						<div class="criteriaName"> Criterium <span class="criteriaName"><xsl:value-of select="@name"/></span></div>
						<div class="criteriaWeight"> Weight <span class="criteriaWeight"><xsl:value-of select="@weight"/></span></div>
					</div>
					<div>
						<xsl:apply-templates select="//criterium[@name=current()/@name]"/>
					</div>
				</div>
			</xsl:for-each>
		</div>
	</div>
</xsl:template>

<xsl:template match="criterium">
	<xsl:for-each select="practice-ref">
		<div class="practice">
			<div class="practiceHeader" onclick="showHide(this);">
				<div class="practiceName"> Practice <span class="practiceName"><xsl:value-of select="@name"/></span></div>
				<div class="practiceWeight"> Weight <span class="practiceWeight"><xsl:value-of select="@weight"/></span></div>
			</div>
			<div>
				<xsl:apply-templates select="//practice[@name=current()/@name]"/>
			</div>
		</div>
	</xsl:for-each>
</xsl:template>

<xsl:template match="practice">
	<xsl:choose>
    	<xsl:when test="simpleformula|conditionformula">
			<xsl:apply-templates select="simpleformula"/>
			<xsl:apply-templates select="conditionformula"/>
        </xsl:when>
        <xsl:otherwise>
        	<div class="practiceNotComputed">
        		Practice not computed by Squale
        	</div>
        </xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="simpleformula">
	<xsl:call-template name="details"/>
	<div class="formula"> 
		<span class="label">Formula used to compute the practice grade:</span>
		<div>
			<xsl:value-of select="formula"/>
		</div>
	</div>
</xsl:template>

<xsl:template match="conditionformula">
	<xsl:call-template name="details"/>
	<div class="formula">
		<span class="label">Conditional formula used to compute the practice grade, according to metric thresholds:</span>
		<div>
			<table class="conditionformula" cellspacing="1px">
				<tr><th align="center">Condition</th><th>Grade</th></tr>
				<tr><td align="center"><xsl:value-of select="conditions/condition[1]"/>  </td><td align="center"> 0 
				</td></tr>
				<tr><td align="center"><xsl:value-of select="conditions/condition[2]"/> </td><td align="center"> 1
				</td></tr>
				<tr><td align="center"><xsl:value-of select="conditions/condition[3]"/> </td><td align="center"> 2
				</td></tr>
				<tr><td align="center">No condition met</td><td align="center"> 3 
				</td></tr>
			</table>
		</div>
	</div>
</xsl:template>

<xsl:template name="details">
	<div class="details">
		<span class="label">Level:</span> <xsl:value-of select="level"/>
		<br/>
		<span class="label">Associated tools:</span> <xsl:apply-templates select="measures/measure"/>
		<br/>
		<xsl:if test="trigger != ''">
			<span class="label">Triggers:</span> <xsl:value-of select="trigger"/>
		</xsl:if>
	</div>
</xsl:template>

<xsl:template match="measure">
	<xsl:if test="position()!=1"> ,
	</xsl:if>
	<xsl:value-of select="."/>
</xsl:template>

</xsl:stylesheet>
