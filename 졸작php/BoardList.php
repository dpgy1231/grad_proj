<?php
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");
  $result = mysqli_query($con, "SELECT * FROM NOTICE ORDER BY noticeIndex DESC;");
  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("noticeIndex"=>$row[0], "noticeTag"=>$row[1], "noticeTitle"=>$row[2],
    "noticeContent"=>$row[3], "noticeWriter"=>$row[4], "noticeDate"=>$row[5], "noticeTime"=>$row[6],
    "noticeCount"=>$row[7], "noticeUserID"=>$row[8], "noticeFile"=>$row[9]));
  }

  echo json_encode(array("response"=>$response));
  mysqli_close($con);
?>
