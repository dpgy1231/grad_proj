<?php
  $con = mysqli_connect("localhost", "coe516", "coe516516!","coe516");

  $commentIndex = $_POST["commentIndex"];
  $response = array();

  $result = mysqli_query($con, "DELETE FROM COMMENT WHERE cmIndex = '$commentIndex'");
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
