<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="MPW46D_kurzusfelvetel">
    <html>
      <head>
        <style>
          table {
            border-collapse: collapse;
            width: 100%;
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
        <h3>Hallgatói adatok</h3>
        <ul>
          <li><strong>Évfolyam: </strong> <xsl:value-of select="hallgato/@evf"/>.</li>
          <li><strong>Név: </strong> <xsl:value-of select="hallgato/hnev"/></li>
          <li><strong>Születési év: </strong> <xsl:value-of select="hallgato/szulev"/></li>
          <li><strong>Szak: </strong> <xsl:value-of select="hallgato/szak"/></li>
        </ul>
        <table>
            <caption>Görög Krisztina Erzsébet Kurzusfelvétel <xsl:value-of select="@tanev"/> félév. <xsl:value-of select="@egyetem"/> egyetem</caption>
          <thead>
            <tr>
              <th>Kurzus ID</th>
              <th>Kurzus Neve</th>
              <th>Kredit</th>
              <th>Hely</th>
              <th>(Időpont) Nap</th>
              <th>Kezdőpont</th>
              <th>Végpont</th>
              <th>Oktató</th>
            </tr>
          </thead>
          <tbody>
            <xsl:apply-templates select="kurzusok/kurzus"/>
          </tbody>
        </table>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="kurzus">
    <tr>
      <td><xsl:value-of select="@id"/></td>
      <td><xsl:value-of select="kurzusnev"/></td>
      <td><xsl:value-of select="kredit"/></td>
      <td><xsl:value-of select="hely"/></td>
      <td><xsl:value-of select="idopont/nap"/></td>
      <td><xsl:value-of select="idopont/kezdopont"/></td>
      <td><xsl:value-of select="idopont/vegpont"/></td>
      <td><xsl:value-of select="oktato"/></td>
    </tr>
  </xsl:template>

</xsl:stylesheet>
