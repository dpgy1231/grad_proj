<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $userID = $_GET["userID"];

  $result = mysqli_query($con, "SELECT COURSE.courseID, COURSE.courseGrade, COURSE.courseTitle, COURSE.courseDivide, COURSE.courseProfessor, COURSE.courseTime, COURSE.courseRoom, COURSE.courseIndex FROM SCHEDULE, COURSE WHERE SCHEDULE.courseIndex IN (SELECT SCHEDULE.courseIndex FROM SCHEDULE WHERE SCHEDULE.userID = '$userID') AND SCHEDULE.courseIndex = COURSE.courseIndex GROUP BY SCHEDULE.courseIndex");

  $response = array();
  while($row = mysqli_fetch_array($result)){
    array_push($response, array("courseID"=>$row[0], "courseGrade"=>$row[1], "courseTitle"=>$row[2], "courseDivide"=>$row[3], "courseProfessor"=>$row[4], "courseTime"=>$row[5], "courseRoom"=>$row[6], "courseIndex"=>$row[7]));
  }
  echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
  mysqli_close($con);
?>
