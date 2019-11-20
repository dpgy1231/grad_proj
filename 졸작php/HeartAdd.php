<?php
  $con = mysqli_connect("localhost","coe516","coe516516!","coe516");

  $communityIndex = $_POST["communityIndex"];
  $userID = $_POST["userID"];

  $statement = mysqli_prepare($con, "INSERT INTO COMMUNITYHEART(communityIndex, userID) VALUES('$communityIndex','$userID')");
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
