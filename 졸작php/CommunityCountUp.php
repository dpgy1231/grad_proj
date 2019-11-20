<?php

  $con = mysqli_connect("localhost","coe516","coe516516!","coe516");

  $communityIndex = $_POST["communityIndex"];
  $communityCount = $_POST["communityCount"];

  $statement = mysqli_prepare($con, "UPDATE COMMUNITY SET communityCount='$communityCount' WHERE communityIndex = '$communityIndex'");

  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
