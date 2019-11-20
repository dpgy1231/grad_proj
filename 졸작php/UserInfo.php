<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $userID = $_GET["userID"];

  $result = mysqli_query($con, "SELECT * FROM USER WHERE userID = '$userID'");
  $response = array();
  while($row = mysqli_fetch_array($result)){
    array_push($response, array("userID"=>$row[0], "userPassword"=>$row[1], "userName"=>$row[2], "userGender"=>$row[3], "userGrade"=>$row[4], "userEmail"=>$row[5], "userPhone"=>$row[6], "userPriority"=>$row[7]));
  }

  echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
  mysqli_close($con);
?>
