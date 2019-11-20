<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $result = mysqli_query($con, "SELECT communityIndex, userID FROM COMMUNITYHEART");
  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("communityIndex"=>$row[0], "userID"=>$row[1]));
  }

  echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
  mysqli_close($con);
?>
