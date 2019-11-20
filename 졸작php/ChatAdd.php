<?php

  $con = mysqli_connect("localhost","coe516","coe516516!","coe516");

  $chatID1 = $_POST["chatID1"];
  $chatName1 = $_POST["chatName1"];
  $chatID2 = $_POST["chatID2"];
  $chatName2 = $_POST["chatName2"];
  $chatContent = $_POST["chatContent"];
  $chatDate = $_POST["chatDate"];
  $chatTime = $_POST["chatTime"];

  $statement = mysqli_prepare($con, "INSERT INTO CHAT(chatID1, chatName1, chatID2, chatName2, chatContent, chatDate, chatTime) VALUES('$chatID1', '$chatName1', '$chatID2', '$chatName2', '$chatContent', '$chatDate', '$chatTime')");
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
