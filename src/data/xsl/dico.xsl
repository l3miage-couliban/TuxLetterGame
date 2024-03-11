<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : dico.xsl
    Created on : 17 octobre 2023, 16:45
    Author     : couliban
    Description:
        Purpose of transformation follows.
-->

<xs:stylesheet version="1.0"
    xmlns:xs="http://www.w3.org/1999/XSL/Transform"
    xmlns:dico="http://myGame/tux">
    <xs:output method="html"/>

    <!-- Afficher un tableau avec comme titre Mot / Niveau
    -->
    <xs:template match="/">
        <html>
            <head>
                <title>dico.xsl</title>
                <link rel="stylesheet" href="../css/dico.css" />
            </head>
            <body>
                <h1>Dictionnaire</h1>
                <p>Dans le dictionnaire, il y a <xs:value-of select="count(dico:dictionnaire/dico:niveau/dico:mot)"/> mots</p>
                <div class="dico">
                    <div>
                        <table>
                            <tr>
                                <th>Niveau 1</th>
                            </tr>
                            <!-- Trier les mots dans l'ordre alphabétique ascendent -->
                            <xs:apply-templates select="dico:dictionnaire/dico:niveau[@numero='1']/dico:mot">
                                <xs:sort select="text()" order="ascending"/>
                            </xs:apply-templates>
                        </table>
                    </div>
                    
                    <div>
                        <table>
                            <tr>
                                <th>Niveau 2</th>
                            </tr>
                            <!-- Trier les mots dans l'ordre alphabétique ascendent -->
                            <xs:apply-templates select="dico:dictionnaire/dico:niveau[@numero='2']/dico:mot">
                                <xs:sort select="text()" order="ascending"/>
                            </xs:apply-templates>
                        </table>
                    </div>
                    
                    <div>
                        <table>
                            <tr>
                                <th>Niveau 3</th>
                            </tr>
                            <!-- Trier les mots dans l'ordre alphabétique ascendent -->
                            <xs:apply-templates select="dico:dictionnaire/dico:niveau[@numero='3']/dico:mot">
                                <xs:sort select="text()" order="ascending"/>
                            </xs:apply-templates>
                        </table>
                    </div>
                    
                    <div>
                        <table>
                            <tr>
                                <th>Niveau 4</th>
                            </tr>
                            <!-- Trier les mots dans l'ordre alphabétique ascendent -->
                            <xs:apply-templates select="dico:dictionnaire/dico:niveau[@numero='4']/dico:mot">
                                <xs:sort select="text()" order="ascending"/>
                            </xs:apply-templates>
                        </table>
                    </div>
                    
                    <div>
                        <table>
                            <tr>
                                <th>Niveau 5</th>
                            </tr>
                            <!-- Trier les mots dans l'ordre alphabétique ascendent -->
                            <xs:apply-templates select="dico:dictionnaire/dico:niveau[@numero='5']/dico:mot">
                                <xs:sort select="text()" order="ascending"/>
                            </xs:apply-templates>
                        </table>
                    </div>
                    
                   
                </div>
            </body>
        </html>
    </xs:template>
    
    <!-- Une ligne dans laquelle il y'a une cellule contenant le mot -->
    <xs:template match="dico:mot">
        <tr>
            <td>
                <xs:value-of select="text()"/>                
            </td>
        </tr>
    </xs:template>

</xs:stylesheet>
