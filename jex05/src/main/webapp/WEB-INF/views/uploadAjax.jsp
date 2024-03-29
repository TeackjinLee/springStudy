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
		display:flex;
		flex-flow: row;
		justify-content: center;
		align-items: center;
	}
	
	.uploadResult ul li {
		list-style: none;
		padding : 10px;
		align-content: center;
		text-align: center;
	}
	
	.uploadResult ul li img {
		width : 20px;
	}

	.uploadResult ul li span {
		color:white;
	}

	.bigPictureWrapper {
		position: absolute;
		display: none;
		justify-content: center;
		align-items: center;
		top:0%;
		width:100%;
		height: 100%;
		background-color: gray;
		z-index: 100;
		background: rgba(255,255,255,0.5);
	}

	.bigPicture {
		position: relative;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.bigPicture img {
		width: 600px;
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
	
	<div class='bigPictureWrapper'>
		<div class='bigPicture'>
		</div>
	</div>
		
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
						var fileCallPath = encodeURIComponent(i.uploadPath + "/" + i.uuid + "_" + i.fileName);
						
						var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
						
						str += "<li><div><a href='/download?fileName="+ fileCallPath +"'><img src='/resources/img/attach.png'>" + i.fileName + "</a>" + 
								"<span data-file=\'" + fileCallPath + "\' data-type='file'> X </span>" +
								"</div></li>";
					} else {
						// str += "<li>" + i.fileName + "</li>";
						var fileCallPath = encodeURIComponent(i.uploadPath + "/s_" + i.uuid + "_" + i.fileName);
						
						var originPath = i.uploadPath + "\\" + i.uuid + "_" + i.fileName;
						
						originPath = originPath.replace(new RegExp(/\\/g),"/");

						str += "<li><a href=\"javascript:showImage(\'" + originPath + "\')\"><img src='/display?fileName=" + fileCallPath + "' /></a>" + 
							"<span data-file=\'" + fileCallPath + "\' data-type='image'> X </span>" +
							"</li>";
					}
				});
				
				uploadResult.innerHTML += str;
			}
		    
			function showImage(fileCallPath) {
				// alert(fileCallPath);
				let bigPictureWrapper = document.querySelector('.bigPictureWrapper');
				bigPictureWrapper.style.display = 'flex';
				
				let bigPicture = document.querySelector('.bigPicture');
				bigPicture.innerHTML = "<img src='/display?fileName=" + encodeURI(fileCallPath) + "'>";

				var imgElement = bigPicture.querySelector("img");

				var animationOptions = {
					width : '100%',
					height : '100%',
				};
				
				var animationHideOptions = {
						width : '0%',
						height : '0%',
					};
				
				animateElement(imgElement, animationOptions, 1000);

				bigPictureWrapper.addEventListener("click", function(e){
					animateElement(imgElement, animationHideOptions, 1000);
					setTimeout(() => {
						this.style.display = "none";
					}, 1000);
				});
			}

			function animateElement(element, options, duration) {
				element.style.transition = "all " + duration / 1000 + "s";
				Object.keys(options).forEach(function (property) {
					element.style[property] = options[property];
				});
			}

			let uploadResultElement = document.querySelector(".uploadResult");
			uploadResultElement.addEventListener("click", function(e){
				console.log(e.target);
				if(e.target.tagName === "span") {
					var targetFile = e.target.dataset.file;
        			var type = e.target.dataset.type;
					console.log("type :" + type);
					console.log(targetFile);

					$.ajax({
						url : '/deleteFile',
						data : {fileName: targetFile, type: type},
						dataType : 'text',
						type : 'POST',
						success : function(result) {
							alert(result);
							targetLi.remove();
						}
					});
				}				

			});

			
	</script>
</body>
</html>