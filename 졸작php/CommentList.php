<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $communityIndex = $_GET["communityIndex"];
  $result = mysqli_query($con, "SELECT * FROM COMMENT WHERE communityIndex = '$communityIndex' ORDER BY cmIndex;");
  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("cmIndex"=>$row[0], "communityIndex"=>$row[1], "cmuserName"=>$row[2],
    "cmContent"=>$row[3], "cmDate"=>$row[4], "cmTime"=>$row[5], "cmuserID"=>$row[6],
    "cmTag"=>$row[7]));
  }

  echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
  mysqli_close($con);
?>
