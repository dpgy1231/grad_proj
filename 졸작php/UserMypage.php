<?php
  $con = mysqli_connect("localhost","coe516","coe516516!","coe516");

  $userID = $_POST["userID"];
  $userPassword = $_POST["userPassword"];
  $userName = $_POST["userName"];
  $userGender = $_POST["userGender"];
  $userGrade = $_POST["userGrade"];
  $userEmail = $_POST["userEmail"];
  $userPhone = $_POST["userPhone"];

  $statement = mysqli_prepare($con, "UPDATE USER SET userPassword='$userPassword', userName='$userName', userGender='$userGender', userGrade='$userGrade', userEmail='$userEmail', userPhone='$userPhone' WHERE userID = '$userID'");
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
