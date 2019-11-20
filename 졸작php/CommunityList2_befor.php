<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");

  $result = mysqli_query($con, "SELECT CU.communityIndex, CH.userID, CO.cmIndex FROM COMMUNITY CU LEFT OUTER JOIN COMMUNITYHEART CH ON CU.communityIndex = CH.communityIndex LEFT OUTER JOIN COMMENT CO ON CU.communityIndex = CO.communityIndex");
  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("communityIndex"=>$row[0], "userID"=>$row[1], "cmIndex"=>$row[2]));
  }

  echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
  mysqli_close($con);
?>
