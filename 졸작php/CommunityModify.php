<?php

  $con = mysqli_connect("localhost","coe516","coe516516!","coe516");

  $communityIndex = $_POST["communityIndex"];
  $communityTag = $_POST["communityTag"];
  $communityTitle = $_POST["communityTitle"];
  $communityContent = $_POST["communityContent"];
  $communityDate = $_POST["communityDate"];
  $communityTime = $_POST["communityTime"];
  $communityUserID = $_POST["communityUserID"];

  $statement = mysqli_prepare($con, "UPDATE COMMUNITY SET communityTag='$communityTag', communityTitle='$communityTitle', communityContent='$communityContent', communityDate='$communityDate', communityTime='$communityTime', communityUserID='$communityUserID' WHERE communityIndex='$communityIndex'");

  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
