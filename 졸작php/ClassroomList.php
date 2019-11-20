<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $courseYear = $_GET["courseYear"];
  $courseTerm = $_GET["courseTerm"];

  $result = mysqli_query($con, "SELECT courseTitle, courseTime, courseRoom FROM COURSE WHERE courseYear = '$courseYear' AND courseTerm = '$courseTerm'");
  $response = array();
  while($row = mysqli_fetch_array($result)){
    array_push($response, array("courseTitle"=>$row[0], "courseTime"=>$row[1], "courseRoom"=>$row[2]));
  }

  echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
  mysqli_close($con);
?>
