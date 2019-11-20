<?php
  $con = mysqli_connect("localhost", "coe516", "coe516516!","coe516");

  $chatIndex = $_POST["chatIndex"];
  $response = array();

  $result = mysqli_query($con, "DELETE FROM CHAT WHERE chatIndex = '$chatIndex'");
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
