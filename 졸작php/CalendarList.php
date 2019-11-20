<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $scYear = $_GET["scYear"];
  $scMonth = $_GET["scMonth"];
  $result = mysqli_query($con, "SELECT * FROM SCHOOL WHERE scYear = '$scYear' AND scMonth = '$scMonth' ORDER BY scDate;");
  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("scIndex"=>$row[0], "scContent"=>$row[1], "scYear"=>$row[2],
    "scMonth"=>$row[3], "scDate"=>$row[4]));
  }

  echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
  mysqli_close($con);
?>
