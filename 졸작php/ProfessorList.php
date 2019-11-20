<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $result = mysqli_query($con, "SELECT userName FROM USER WHERE userPriority = 2;");
  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("userName"=>$row[0]));
  }

  echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
  mysqli_close($con);
?>
