<?php 
  include './header.php'; 
  include './funcs1.php';

  $conn = OpenCon();


  $start = get_start();


  $result = mysqli_query($conn,"select * from t_subject order by name asc LIMIT ".$start.", ".$perpage.";");
?>
<!-- oktavia -->
<form id="searchform">
<input class="search" type="search" name="search" id="search" results="5" value="" placeholder="Search" />
<div id="searchresult_box">
<div id="close_search_box">&times;</div>
<div id="searchresult_summary"></div>
<div id="searchresult"></div>
<div id="searchresult_nav"></div>
<span class="pr">Powered by <a href="https://github.com/shibukawa/oktavia">Oktavia</a></span>
</div>
</form>
<!-- /oktavia -->

</div>

<table id="menu">
<tr>
<td class="selected"><a href="home.php">极客时间</a></td>
<td><a href="install.html">Install</a></td>
<td><a href="configure.html">Configure</a></td>
<td><a href="faq.html">FAQ</a></td>
<td><a href="http://blog.kazuhooku.com/search/label/H2O" target="_blank">Blog</a></td>
<td><a href="http://github.com/h2o/h2o/" target="_blank">Source</a></td>
</tr>
</table>

<div id="main">

<h2>
极客时间
</h2>


<ul style="list-style: none; font-weight: bold;">
<li><a href="configure/quick_start.html">专栏</a> / <a href="configure/command_options.html">视频</a></li>

<li>首页--专栏
<ul>

<?php 

while($row = mysqli_fetch_array($result))
{
 
  echo "<li><a target=_blank href='list.php?sid=".$row['id']."'>". $row['name'] . "</a></li>";
}

?>
<!-- <li><a href="configure/base_directives.html">Base</a> -->

</ul>
</li>

</ul>
<li>
  <?php
  if($start >= $perpage){
    $prev = $start - $perpage;
    echo "<a href='?start=$prev' > 上一页</a> / ";
  }
  $next = $start + $perpage;
  echo "<a href='?start=$next' > 下一页</a>";
  ?>
</li>
</div>

<?php 
  include './footer.php'; 
?>