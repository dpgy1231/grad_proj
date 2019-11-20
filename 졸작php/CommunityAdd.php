<?php

  $con = mysqli_connect("localhost","coe516","coe516516!","coe516");

  $communityTag = $_POST["communityTag"];
  $communityTitle = $_POST["communityTitle"];
  $communityContent = $_POST["communityContent"];
  $communityWriter = $_POST["communityWriter"];
  $communityDate = $_POST["communityDate"];
  $communityTime = $_POST["communityTime"];
  $communityUserID = $_POST["communityUserID"];

  $statement = mysqli_prepare($con, "INSERT INTO COMMUNITY(communityTag, communityTitle, communityContent, communityWriter, communityDate, communityTime, communityUserID) VALUES('$communityTag', '$communityTitle', '$communityContent', '$communityWriter', '$communityDate', '$communityTime', '$communityUserID')");
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
