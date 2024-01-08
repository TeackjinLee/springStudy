<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.uploadResult {
		width: 100%;
		background-color : gray;
	}
	
	.uploadResult ul {
		disply:flex;
		flex-flow: row;
		justify-content: center;
		align-items: center;
	}
	
	.uploadResult ul li {
		list-style: none;
		padding : 10px;
	}
	
	.uploadResult ul li img {
		width : 20px;
	}
</style>
</head>
<body>
	<h1>Upload with Ajax</h1>
	
	<div class="uploadDiv">
		<input type="file" name="uploadFile" multiple>
	</div>
	
	<div class='uploadResult'>
		<ul>
		</ul>
	</div>
	
	<button id='uploadBtn'>Upload</button>
		
	<script src="https://code.jquery.com/jquery-3.4.1.js"   
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="   
	crossorigin="anonymous"></script>

	<script>
		/* $(document).ready(function(){
			$("#uploadBtn").on("click", function(e){

				var formData = new FormData();

				var inputFile = $("input[name='uploadFile']");

				var files = inputFile[0].files;
				console.log(files);
			});
		}); */
		
	
		    var uploadBtn = document.getElementById("uploadBtn");
			var cloneObj = document.querySelector(".uploadDiv").cloneNode(true);

		    uploadBtn.addEventListener("click", function (e) {
		        var formData = new FormData();

		        var inputFile = document.querySelector("input[name='uploadFile']");

		        var files = inputFile.files;
		        console.log(files);
		        
		        // add File Data to formData
		        for(var i=0; i<files.length; i++){
		        	
		        	if (!checkExtension(files[i].name, files[i].size)){
		        		return false;
		        	}
		        	
		        	formData.append("uploadFile", files[i]);
		        }
		        
		        $.ajax({
					url: '/uploadAjaxAction', 
					processData : false,
					contentType: false,
					data: formData,
					type: 'POST',
					dataType: 'json',
					success: function(result) {
						console.log(result);

						showUploadedFile(result);

						var uploadDiv = document.querySelector(".uploadDiv");
						uploadDiv.innerHTML = cloneObj.innerHTML;
						//
					}
				});	//$.ajax
		    });
		    
		    var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
		    var maxSize = 5242880; // 5MB
		    
		    function checkExtension(fileName, fileSize) {
		    	if (fileSize >= maxSize) {
		    		alert("파일 사이즈 초과")
		    		return false;
		    	}
		    	
		    	if (regex.test(fileName)) {
		    		alert("해당 종류의 파일은 업로드할 수 없습니다.");
		    		return false;
		    	}
		    	return true;
		    }

			let uploadResult = document.querySelector(".uploadResult ul");

			function showUploadedFile(uploadResultArr) {
				let str = "";

				let state = {
					items : [...uploadResultArr]
				};

				state.items.forEach(i => {
					console.log(i);
					if (!i.image) {
						str += "<li><img src='/resources/img/attach.png'>" + i.fileName + "</li>";
					} else {
						// str += "<li>" + i.fileName + "</li>";
						var fileCallPath = encodeURIComponent(i.uploadPath + "/s_" + i.uuid + "_" + i.fileName);

						str += "<li><img src='/display?fileName=" + fileCallPath +"'></li>";
					}
				});
				
				uploadResult.innerHTML += str;
			}
		    	
	</script>
</body>
</html>