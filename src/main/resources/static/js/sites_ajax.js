 $(document).ready(function(){

  $.getJSON( "http://api.stackexchange.com/2.2/sites?filter=!2--Yion.2OJSStcKSpFvq", function( data ) {
  
  $.each( data.items, function( key, val ) {
	    $(".for_append").append("<tr>");
	    $(".for_append").append("<td><img src =  "+val.favicon_url+"     /></td>");
	    $(".for_append").append("<td>"+val.site_url+"</td>");
	    $(".for_append").append("<td>"+val.name+"</td>");
	    $(".for_append").append("</tr>");

  });
 
  });
  
  });