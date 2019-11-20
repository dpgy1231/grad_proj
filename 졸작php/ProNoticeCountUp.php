<?php

  $con = mysqli_connect("localhost","coe516","coe516516!","coe516");

  $proIndex = $_POST["proIndex"];
  $proCount = $_POST["proCount"];

  $statement = mysqli_prepare($con, "UPDATE PRONOTICE SET proCount='$proCount' WHERE proIndex = '$proIndex'");

  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
