<?php
  $con = mysqli_connect("localhost", "coe516", "coe516516!","coe516");

  $proIndex = $_POST["proIndex"];
  $response = array();

  $result = mysqli_query($con, "DELETE FROM PRONOTICE WHERE proIndex = '$proIndex'");
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
