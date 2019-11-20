<?php
  $con = mysqli_connect("localhost","coe516","coe516516!","coe516");

  $userID = $_POST["userID"];
  $userPassword = $_POST["userPassword"];
  $userName = $_POST["userName"];
  $userGender = $_POST["userGender"];
  $userGrade = $_POST["userGrade"];
  $userEmail = $_POST["userEmail"];
  $userPhone = $_POST["userPhone"];
  $userPriority = $_POST["userPriority"];

  $statement = mysqli_prepare($con, "INSERT INTO USER VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
  mysqli_stmt_bind_param($statement, "dsssdsdd", $userID, $userPassword, $userName, $userGender, $userGrade, $userEmail, $userPhone, $userPriority);
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
