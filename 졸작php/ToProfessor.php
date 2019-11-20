<?php

  $con = mysqli_connect("localhost","coe516","coe516516!","coe516");

  $forProTag = $_POST["forProTag"];
  $forProName = $_POST["forProName"];
  $forProTitle = $_POST["forProTitle"];
  $forProContent = $_POST["forProContent"];
  $forProWriter = $_POST["forProWriter"];
  $forProDate = $_POST["forProDate"];
  $forProTime = $_POST["forProTime"];
  $forProUserID = $_POST["forProUserID"];

  $statement = mysqli_prepare($con, "INSERT INTO FORPRO(forProTag, forProName, forProTitle, forProContent, forProWriter, forProDate, forProTime, forProUserID) VALUES('$forProTag', '$forProName', '$forProTitle', '$forProContent', '$forProWriter', '$forProDate', '$forProTime', '$forProUserID')");
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
