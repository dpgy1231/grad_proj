<?php

  $con = mysqli_connect("localhost","coe516","coe516516!","coe516");

  $noticeIndex = $_POST["noticeIndex"];
  $noticeCount = $_POST["noticeCount"];

  $statement = mysqli_prepare($con, "UPDATE NOTICE SET noticeCount='$noticeCount' WHERE noticeIndex = '$noticeIndex'");

  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
