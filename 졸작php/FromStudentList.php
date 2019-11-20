 <?php
  header("Content-Type: text/html; charset=UTF-8");

  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $forProName = $_GET["forProName"];

  $result = mysqli_query($con, "SELECT * FROM FORPRO WHERE forProName = '$forProName' ORDER BY forProIndex DESC;");
  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("forProIndex"=>$row[0], "forProTag"=>$row[1], "forProName"=>$row[2],
    "forProTitle"=>$row[3], "forProContent"=>$row[4], "forProWriter"=>$row[5], "forProDate"=>$row[6],
    "forProTime"=>$row[7], "forProUserID"=>$row[8]));
  }

  echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
  mysqli_close($con);
?>
