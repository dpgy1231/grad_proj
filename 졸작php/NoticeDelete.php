<?php
  $con = mysqli_connect("localhost", "coe516", "coe516516!","coe516");

  $noticeIndex = $_POST["noticeIndex"];
  $response = array();

  $result = mysqli_query($con, "DELETE FROM NOTICE WHERE noticeIndex = '$noticeIndex'");
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
