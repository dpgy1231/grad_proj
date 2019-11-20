<?php

  $con = mysqli_connect("localhost","coe516","coe516516!","coe516");

  $proTag = $_POST["proTag"];
  $proTitle = $_POST["proTitle"];
  $proContent = $_POST["proContent"];
  $proWriter = $_POST["proWriter"];
  $proDate = $_POST["proDate"];
  $proTime = $_POST["proTime"];
  $proUserID = $_POST["proUserID"];

  $statement = mysqli_prepare($con, "INSERT INTO PRONOTICE(proTag, proTitle, proContent, proWriter, proDate, proTime, proUserID) VALUES('$proTag', '$proTitle', '$proContent', '$proWriter', '$proDate', '$proTime', '$proUserID')");
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
