<?php
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $userID = $_POST["userID"];
  $courseIndex = $_POST["courseIndex"];

  $statement = mysqli_prepare($con, "DELETE FROM SCHEDULE WHERE userID = '$userID' AND courseIndex = '$courseIndex'");
  mysqli_stmt_bind_param($statement, "si", $userID, $courseIndex);
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
