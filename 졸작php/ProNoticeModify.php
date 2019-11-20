<?php

  $con = mysqli_connect("localhost","coe516","coe516516!","coe516");

  $proIndex = $_POST["proIndex"];
  $proTag = $_POST["proTag"];
  $proTitle = $_POST["proTitle"];
  $proContent = $_POST["proContent"];
  $proDate = $_POST["proDate"];
  $proTime = $_POST["proTime"];
  $proUserID = $_POST["proUserID"];

  $statement = mysqli_prepare($con, "UPDATE PRONOTICE SET proTag='$proTag', proTitle='$proTitle', proContent='$proContent', proDate='$proDate', proTime='$proTime', proUserID='$proUserID' WHERE proIndex = '$proIndex'");

  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
