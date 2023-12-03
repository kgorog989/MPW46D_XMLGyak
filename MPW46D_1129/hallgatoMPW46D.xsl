<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="class">
    <html>
      <head>
        <style>
          table {
            border-collapse: collapse;
          }

          th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
          }

          th {
            background-color: #34ffff;
          }
        </style>
      </head>
      <body>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Keresztnév</th>
              <th>Vezetéknév</th>
              <th>Becenév</th>
              <th>Kor</th>
              <th>Ösztöndíj</th>
            </tr>
          </thead>
          <tbody>
            <xsl:for-each select="student">
              <xsl:call-template name="studentTemplate"/>
            </xsl:for-each>
          </tbody>
        </table>
      </body>
    </html>
  </xsl:template>

  <xsl:template name="studentTemplate">
    <tr>
      <td><xsl:value-of select="@id"/></td>
      <td><xsl:value-of select="keresztnev"/></td>
      <td><xsl:value-of select="vezeteknev"/></td>
      <td><xsl:value-of select="becenev"/></td>
      <td><xsl:value-of select="kor"/></td>
      <td><xsl:value-of select="osztondij"/></td>
    </tr>
  </xsl:template>

</xsl:stylesheet>
