<?php

  $con = mysqli_connect("localhost","coe516","coe516516!","coe516");

  $communityIndex = $_POST["communityIndex"];
  $commentTag = $_POST["commentTag"];
  $commentWriter = $_POST["commentWriter"];
  $commentContent = $_POST["commentContent"];
  $commentDate = $_POST["commentDate"];
  $commentTime = $_POST["commentTime"];
  $commentUserID = $_POST["commentUserID"];

  $statement = mysqli_prepare($con, "INSERT INTO COMMENT(communityIndex, cmuserName, cmContent, cmDate, cmTime, cmuserID, cmTag) VALUES('$communityIndex', '$commentWriter', '$commentContent', '$commentDate', '$commentTime', '$commentUserID', '$commentTag')");
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
