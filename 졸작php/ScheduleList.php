<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $userID = $_GET["userID"];

  $result = mysqli_query($con, "SELECT COURSE.courseID, COURSE.courseTime, COURSE.courseProfessor, COURSE.courseTitle, COURSE.courseIndex FROM USER, COURSE, SCHEDULE WHERE USER.userID = '$userID' AND USER.userID = SCHEDULE.userID AND SCHEDULE.courseIndex = COURSE.courseIndex");
  $response = array();
  while($row = mysqli_fetch_array($result)){
    array_push($response, array("courseID"=>$row[0], "courseTime"=>$row[1], "courseProfessor"=>$row[2], "courseTitle"=>$row[3], "courseIndex"=>$row[4]));
  }

  echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
  mysqli_close($con);
?>
