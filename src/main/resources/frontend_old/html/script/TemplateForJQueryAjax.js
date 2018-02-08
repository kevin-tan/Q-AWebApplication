//EXAMPLE OF A GET REQUEST TO BACKEND FOR ALL QUESTIONS
//$(document).ready(function(){
//    var results = [];
//    $.ajax({
//       type: "GET",
//       url: "http://localhost:8080/questions",
//       dataType: "json",
//       contentType: "application/json"
//       success: function(data){
//         //Success use data to retrieve all values
//         results = data;
//       },
//       error: function(e){
//         //Error display messgge
//       }
//     });
//});


//THIS IS FOR A BUTTON TO BE USED AS A onclick="postData();"
//    function postData(){
//        $.ajax({
//            type: "POST",
//            url: "http://localhost:8080/questions",
//            dataType: "json",
//            contentType: "application/json",
//            data: JSON.stringify({'message': $("#message").val(), 'author': $("#author").val()})
//        });
//    }

//THIS IS FOR A POST/PUT/DELETE REQUEST TO BACKEND
//$(document).ready(function(){
//    $("#form").submit(function(e){
//        e.preventDefault();
//        $.ajax({
//            url: "http://localhost:8080/questions",
//            type: "POST",
//            dataType: "json",
//            contentType: "application/json",
//            data: JSON.stringify({'message': $("#message").val(), 'author': $("#author").val()})
//        })
//    });
//});