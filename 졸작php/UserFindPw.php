<?php
  $con = mysqli_connect("localhost","coe516","coe516516!","coe516");

  $userID = $_POST["userID"];
  $userPassword = $_POST["userPassword"];
  $userEmail = $_POST["userEmail"];

  $statement = mysqli_prepare($con, "UPDATE USER SET userPassword = '$userPassword' WHERE userID = '$userID' AND userEmail = '$userEmail'");
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
