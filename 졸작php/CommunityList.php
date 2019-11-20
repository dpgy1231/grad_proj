<?php
  $con = mysqli_connect("localhost", "coe516", "coe516516!", "coe516");
  $result = mysqli_query($con, "SELECT * FROM COMMUNITY ORDER BY communityIndex DESC;");
  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("communityIndex"=>$row[0], "communityTag"=>$row[1], "communityTitle"=>$row[2],
    "communityContent"=>$row[3], "communityWriter"=>$row[4], "communityDate"=>$row[5], "communityTime"=>$row[6],
    "communityCount"=>$row[7], "communityUserID"=>$row[8], "file"=>$row[9]));
  }

  echo json_encode(array("response"=>$response));
  mysqli_close($con);
?>
