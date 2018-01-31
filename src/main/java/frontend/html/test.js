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

$(document).ready(function(){
    $("#form").ajaxSubmit({
        type: 'POST',
        url: 'http://localhost:8080/questions'
        contentType: "application/json"
        data: JSON.stringify({"message": $('#fname').val(), "author": $('#lname').val()})
    })
})
})
