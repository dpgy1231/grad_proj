<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $proTag = $_GET["proTag"];
  $result = mysqli_query($con, "SELECT * FROM PRONOTICE WHERE proTag = '$proTag' ORDER BY proIndex DESC;");
  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("proIndex"=>$row[0], "proTag"=>$row[1], "proTitle"=>$row[2],
    "proContent"=>$row[3], "proWriter"=>$row[4], "proDate"=>$row[5], "proTime"=>$row[6],
    "proCount"=>$row[7], "proUserID"=>$row[8], "proFile"=>$row[9]));
  }

  echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
  mysqli_close($con);
?>
