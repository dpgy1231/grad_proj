<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $userID = $_GET["userID"];
  $result = mysqli_query($con, "SELECT userID, userName FROM USER WHERE userID != '$userID';");
  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("userID"=>$row[0], "userName"=>$row[1]));
  }

  echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
  mysqli_close($con);
?>
