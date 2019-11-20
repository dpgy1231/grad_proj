<?php

  $con = mysqli_connect("localhost","coe516","coe516516!","coe516");

  $noticeTag = $_POST["noticeTag"];
  $noticeTitle = $_POST["noticeTitle"];
  $noticeContent = $_POST["noticeContent"];
  $noticeDate = $_POST["noticeDate"];
  $noticeTime = $_POST["noticeTime"];
  $noticeUserID = $_POST["noticeUserID"];

  $statement = mysqli_prepare($con, "INSERT INTO NOTICE(noticeTag, noticeTitle, noticeContent, noticeDate, noticeTime, noticeUserID) VALUES('$noticeTag', '$noticeTitle', '$noticeContent', '$noticeDate', '$noticeTime', '$noticeUserID')");
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
