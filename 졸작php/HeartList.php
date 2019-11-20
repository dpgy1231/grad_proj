<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $communityIndex = $_GET["communityIndex"];

  $result = mysqli_query($con, "SELECT * FROM COMMUNITYHEART WHERE communityIndex = '$communityIndex' ORDER BY heartIndex DESC;");
  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("heartIndex"=>$row[0], "communityIndex"=>$row[1], "userID"=>$row[2]));
  }

  echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
  mysqli_close($con);
?>
