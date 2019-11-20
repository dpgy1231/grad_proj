<?php

  $con = mysqli_connect("localhost","coe516","coe516516!","coe516");

  $noticeIndex = $_POST["noticeIndex"];
  $noticeTag = $_POST["noticeTag"];
  $noticeTitle = $_POST["noticeTitle"];
  $noticeContent = $_POST["noticeContent"];
  $noticeDate = $_POST["noticeDate"];
  $noticeTime = $_POST["noticeTime"];
  $noticeUserID = $_POST["noticeUserID"];

  $statement = mysqli_prepare($con, "UPDATE NOTICE SET noticeTag='$noticeTag', noticeTitle='$noticeTitle', noticeContent='$noticeContent', noticeDate='$noticeDate', noticeTime='$noticeTime', noticeUserID='$noticeUserID' WHERE noticeIndex = '$noticeIndex'");

  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);
?>
