//$(document).ready(function(){
//	$.ajax({
//		url: "http://localhost:8080/questions",
//        type: 'GET',
//        dataType: 'json'
//	}).then(function(data, status, jqxhr){
//	    var insert = "";
//	    $(#textField).val();
//        $.each(data, function(){
//
//        });
//	});
//});

    function postData(){
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/questions",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({'message': $("#message").val(), 'author': $("#author").val()})
        });
    }