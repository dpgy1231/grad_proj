<?php
  $con = mysqli_connect("localhost", "coe516", "coe516516!","coe516");

  $communityIndex = $_POST["communityIndex"];
  $response = array();

  $result = mysqli_query($con, "DELETE FROM COMMUNITY WHERE communityIndex = '$communityIndex'");
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
