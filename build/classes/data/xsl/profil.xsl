<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : profil.xsl
    Created on : 17 octobre 2023, 17:48
    Author     : couliban
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:pro="http://myGame/tux">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <head>
                <title>profil.html</title>
                <link rel="stylesheet" href="../css/profil.css" />
                <link rel="stylesheet" href="../../css/profil.css" />
            </head>
            <body>
                <div class="container">
                    <div class="content">
                        <xsl:element name="img">
                            <xsl:attribute name="class">avatar y-axis</xsl:attribute>
                            <xsl:attribute name="src">
                                <xsl:value-of select="pro:profil/pro:avatar" />
                            </xsl:attribute>
                            <xsl:attribute name="alt">avatar</xsl:attribute>
                        </xsl:element>
                    
                        <h3>Nom: <xsl:value-of select="pro:profil/pro:nom"/></h3>
                        <h4>Date de naissance: <xsl:value-of select="pro:profil/pro:anniversaire"/></h4>
                        <h1>Parties</h1>
                        <br/>
                        <div class="parties-area">
                            <table class="parties">
                                <tr>
                                    <th>ID</th>
                                    <th>Date</th>
                                    <th>Temps</th>
                                    <th>Niveau</th>
                                    <th>Score</th>
                                    <th>Mot</th>
                                </tr>
                                <xsl:apply-templates select="pro:profil/pro:parties/pro:partie">
                                    <xsl:sort select="@date" order="descending"/>
                                </xsl:apply-templates>
                            </table>
                        </div>
                    </div>
                </div>
            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="pro:partie">
        <tr>
            <td>
                <xsl:value-of select="@id"/>
            </td>
            <td>
                <xsl:value-of select="@date"/>
            </td>
            <td>
                <xsl:value-of select="pro:temps"/>
            </td>
            <td>
                <xsl:value-of select="pro:mot/@niveau"/>
            </td>
            <td>
                <xsl:value-of select="@trouvé"/>
            </td>
            <td>
                <xsl:value-of select="pro:mot"/>
            </td>
        </tr>
    </xsl:template>

</xsl:stylesheet>
