<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $userID = $_GET["userID"];

  $result = mysqli_query($con, "SELECT * FROM CHAT WHERE chatID1 = '$userID' OR chatID2 = '$userID'");
  $response = array();
  while($row = mysqli_fetch_array($result)){
    array_push($response, array("chatIndex"=>$row[0], "chatID1"=>$row[1], "chatName1"=>$row[2], "chatID2"=>$row[3], "chatName2"=>$row[4], "chatContent"=>$row[5], "chatDate"=>$row[6], "chatTime"=>$row[7], "chatCount"=>$row[8]));
  }

  echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
  mysqli_close($con);
?>
