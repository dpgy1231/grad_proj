<?php
  $con = mysqli_connect("localhost", "coe516", "coe516516!","coe516");

  $forProIndex = $_POST["forProIndex"];
  $response = array();

  $result = mysqli_query($con, "DELETE FROM FORPRO WHERE forProIndex = '$forProIndex'");
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
