<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<html>
<head>
<title>Results</title>
<link href="css/body.css" rel="stylesheet" type="text/css" />
</head>

<body>

<jsp:include page="adminHeader.jsp"></jsp:include>

<%
Connection con = null;

try {

    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

    String url =
        "jdbc:sqlserver://localhost:54078;databaseName=evoting;encrypt=false;trustServerCertificate=true";

    con = DriverManager.getConnection(
            url,
            "sa",
            "@ravindh56luffy"
    );

    HashMap<String,Integer> voteMap = new HashMap<>();

    Statement stmt = con.createStatement();

    ResultSet voteRs =
        stmt.executeQuery(
            "SELECT voter, COUNT(*) AS totalVotes " +
            "FROM voter GROUP BY voter"
        );

    while(voteRs.next()){

        String code = voteRs.getString("voter");

        if(code != null){
            code = code.trim().toUpperCase();
        }

        int count = voteRs.getInt("totalVotes");

        voteMap.put(code,count);
    }

    voteRs.close();

    int winnerVotes = 0;

    if(!voteMap.isEmpty()){
        winnerVotes = Collections.max(voteMap.values());
    }
%>

<div class="limiter">

    <br><br><br><br>

    <div class="container-login100">

        <div class="wrap-login100">

            <form action="#" method="post"
                  style="max-width:350px;margin:auto">

                <div class="container" style="width:700px">

                    <ul style="align-content:center">
                        <li style="text-align:center" class="active">
                            <h1>RESULTS</h1>
                        </li>
                    </ul>

                    <hr>

                    <div class="container-table">

                        <table class="table-all">

                            <tr style="text-align:center">
                                <th>Party Code</th>
                                <th>Party Name</th>
                                <th>No. of Votes</th>
                                <th>Status</th>
                            </tr>

<%
    PreparedStatement ps =
        con.prepareStatement(
            "SELECT partyCode, partyName FROM partytable"
        );

    ResultSet rs = ps.executeQuery();

    while(rs.next()){

        String partyCode = rs.getString("partyCode");

        if(partyCode != null){
            partyCode = partyCode.trim().toUpperCase();
        }

        String partyName = rs.getString("partyName");

        int votes = 0;

        if(voteMap.containsKey(partyCode)){
            votes = voteMap.get(partyCode);
        }
%>

<tr>

    <td style="text-align:center">
        <%=partyCode%>
    </td>

    <td style="text-align:center">
        <%=partyName%>
    </td>

    <td style="text-align:center">
        <%=votes%>
    </td>

    <td style="text-align:center">

        <%
        if(votes > 0 && votes == winnerVotes){
        %>
            Winner
        <%
        }
        %>

    </td>

</tr>

<%
    }

    rs.close();
    ps.close();

} catch(Exception e){

    out.println("<h3 style='color:red'>");
    out.println(e.getMessage());
    out.println("</h3>");

    e.printStackTrace();

} finally {

    if(con != null){
        try{
            con.close();
        }catch(Exception ex){
        }
    }
}
%>

                        </table>

                    </div>

                </div>

            </form>

        </div>

        <jsp:include page="adminFooter.jsp"></jsp:include>

    </div>

</div>

</body>
</html>