/**
 * 
 */
 $(function() 
	{
	$('#one').click(function() 
	{ 
	  htmlobj=$.ajax({url:"/jfinalGrid/blog",async:false});
	  $("#myDiv").html(htmlobj.responseText);
	});
	$('#makejava').click(function() 
			{ 
			  htmlobj=$.ajax({url:"/jfinalGrid/blog/frModeltoProg",async:false});
			  $("#myDiv").html(htmlobj.responseText);
			});	
	}
  );