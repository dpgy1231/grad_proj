<?php
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $communityIndex = $_POST["communityIndex"];
  $userID = $_POST["userID"];

  $response = array();

  $statement = mysqli_query($con, "DELETE FROM COMMUNITYHEART WHERE communityIndex = '$communityIndex' AND userID = '$userID'");
  mysqli_stmt_execute($statement);

  $response["success"] = true;

  echo json_encode($response);
?>
