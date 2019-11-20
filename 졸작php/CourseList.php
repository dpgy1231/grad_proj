<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $courseYear = $_GET["courseYear"];
  $courseTerm = $_GET["courseTerm"];
  $courseArea = $_GET["courseArea"];

  $result = mysqli_query($con, "SELECT * FROM COURSE WHERE courseYear = '$courseYear' AND courseTerm = '$courseTerm' AND courseArea = '$courseArea'");
  $response = array();
  while($row = mysqli_fetch_array($result)){
    array_push($response, array("courseID"=>$row[0], "courseUniversity"=>$row[1], "courseYear"=>$row[2],
    "courseTerm"=>$row[3], "courseArea"=>$row[4], "courseMajor"=>$row[5], "courseGrade"=>$row[6],
    "courseTitle"=>$row[7], "courseCredit"=>$row[8], "courseDivide"=>$row[9], "coursePersonnel"=>$row[10],
    "courseProfessor"=>$row[11], "courseTime"=>$row[12], "courseRoom"=>$row[13], "courseIndex"=>$row[14]));
  }

  echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
  mysqli_close($con);
?>
