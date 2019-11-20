<?php
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $userID = $_POST["userID"];
  $courseID = $_POST["courseID"];
  $courseIndex = $_POST["courseIndex"];

  $statement = mysqli_prepare($con, "INSERT INTO SCHEDULE VALUES (?, ?, ?)");
  mysqli_stmt_bind_param($statement, "ssi", $userID, $courseID, $courseIndex);
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
