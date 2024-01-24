<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@include file="../includes/header.jsp" %>

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
	
	<div class="row">
		<div class="col-lg-12">
		    <h1 class="page-header">Board Register</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
	    <div class="col-lg-12">
	        <div class="panel panel-default">
	            <div class="panel-heading">
	                Board Register
	            </div>
	            <!-- /.panel-heading -->
	            <div class="panel-body">
	            	<form role="form" action="/board/register" method="post">
	            		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	            		<div class="form-group">
	            			<label>Title</label>
	            			<input class="form-control" name="title">
	            		</div>
	            		<div class="form-group">
	            			<label>Text area</label>
	            			<textarea class="form-control" rows="3" name="content"></textarea>
	            		</div>
	            		<div class="form-group">
	            			<label>Writer</label>
	            			<input class="form-control" name="writer" value='<sec:authentication property="principal.username"/>' readonly="readonly">
	            		</div>
	            		<button type="submit" class="btn btn-default">Button</button>
	            		<button type="reset" class="btn btn-default">Reset Button</button>
	            	</form>
	            </div>
	            <!-- /.panel-body -->
	        </div>
	        <!-- /.panel -->
	    </div>
	    <!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	
	<!-- 새로 추가하는 부분 -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">File Attach</div>
				<!--  /.panel-heading -->
				<div class="panel-body">
					<div class="form-group uploadDiv">
						<input type="file" name='uploadFile' multiple>
					</div>
					
					<div class='uploadResult'>
						<ul>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
            
<script type="text/javascript">
	
	/* var formObj = document.querySelector("form[role='form']"); */
	
	/* document.querySelector("button[type='submit']").addEventListener("click", function(e){
		 e.preventDefault(); 
		
		console.log("submit clicked");
		
		var str = "";
		var uploadResultListItems = document.querySelectorAll(".uploadResult ul li");

		var i = 0;
		for (i=0; i < uploadResultListItems.length; i++) {
			var element = uploadResultListItems[i];
			var jobj = element;
		    console.dir(jobj);
			
		    str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.dataset.fileName + "'>";
		    str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.dataset.uuid + "'>";
		    str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.dataset.uploadPath + "'>";
		    str += "<input type='hidden' name='attachList[" + i + "].fileType' value='" + jobj.dataset.type + "'>";
		}
		
		 uploadResultListItems.forEach(function (element, i) {
		    var jobj = element;
		    console.dir(jobj);
			
		    str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.dataset.fileName + "'>";
		    str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.dataset.uuid + "'>";
		    str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.dataset.uploadPath + "'>";
		    str += "<input type='hidden' name='attachList[" + i + "].fileType' value='" + jobj.dataset.type + "'>";
		});
		
		console.log("=========================");
		console.log(str);
		console.log("=========================");
	    
		formObj.appendChild(document.createTextNode(str));
	    formObj.submit();
	}); */
	
	/* $(document).ready(function(){
		var formObj = $("form[role='form']");
		
		$("button[type='submit']").on("click", function(e) {
			e.preventDefault();
			console.log("submit clicked");
			
			var str = ";"
			
			$(".uploadResult ul li").each(function(i, obj){
				var jobj = $(obj);
				console.dir(jobj);
				
				str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
			    str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
			    str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
			    str += "<input type='hidden' name='attachList[" + i + "].fileType' value='" + jobj.data("type") + "'>";
			});
			formObj.append(str).submit();
		});
	}); */
	

	var formObj = document.querySelector("form[role='form']");
	var submitButton = document.querySelector("button[type='submit']");
	
	submitButton.addEventListener("click", function (e) {
	    e.preventDefault();
	    console.log("submit clicked");
	
	    var str = "";
	
	    document.querySelectorAll(".uploadResult ul li").forEach(function (element, i) {
	        var jobj = element;
	        console.dir(jobj);
	
	        str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.dataset.filename + "'>";
	        str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.dataset.uuid + "'>";
	        str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.dataset.path + "'>";
	        str += "<input type='hidden' name='attachList[" + i + "].fileType' value='" + jobj.dataset.type + "'>";
	    });
	
	    formObj.insertAdjacentHTML("beforeend", str);
	    formObj.submit();
	});

	
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	var maxSize = 5242880; // 5MB
	
	function chechExtension(fileName, fileSize) {
		
		if (fileSize >= maxSize) {
			alert("파일 사이즈 초과");
			return false;
		}
		
		if (regex.test(fileName)) {
			alert("해당 종류의 파일은 업로드할 수 없습니다.");
			return false;
		}
		return true;
	}
	
	var inputTypeFile = document.querySelector("input[type='file']");

	inputTypeFile.addEventListener("change", function(e) {

		var formData = new FormData();

		var inputFile = document.querySelector("input[name='uploadFile']");

		var files = inputFile.files;

		for (var i=0; i<files.length; i++) {
			if(!chechExtension(files[i].name, files[i].size)) {
				return false;
			}
			formData.append("uploadFile", files[i]);
		}

		$.ajax({
			url : '/uploadAjaxAction',
			processData : false,
			contentType : false,
			data : formData,
			type : 'POST',
			dataType : 'json',
			success : function(result) {
				console.log(result);
				showUploadResult(result);
			}
		});
	});
	
	function showUploadResult(uploadResultArr) {
		if(!uploadResultArr || uploadResultArr.length == 0) {
			return;
		}

		var uploadUL = document.querySelector(".uploadResult ul");

		let str = "";

		let state = {
			items : [...uploadResultArr]
		};

		state.items.forEach(obj => {
			console.log(obj);
			if (obj.image) {
				var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
				str += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "'>";
				str += "<div>";
				str += "<span> " + obj.fileName + "</span>";
				str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button>";
				str += "<br><img src='/display?fileName=" + fileCallPath + "'>";
				str += "</div></li>";
			} else {
				var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);

				var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");

				str += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "'>";
				str += "<div><span> " + obj.fileName + "</span>";
				str += "<button type='button' data-file=\'" + fileCallPath + "\ 'data-type='file' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button>";
				str += "<br><img src='/resources/img/attach.png'>";
				str += "</div></li>";
			}
			
		});
		uploadUL.innerHTML += str;
	}
	
	let uploadResult = document.querySelector(".uploadResult");

	uploadResult.addEventListener("click", function(e){
		if(e.target.tagName === "BUTTON" || e.target.tagName === "button") {
			console.log("delete file");

			var targetFile = e.target.dataset.file;
			var type = e.target.dataset.type;

			var targetLi = findClosestLi(e.target);
			console.log("targetLi : " + targetLi);

			$.ajax({
				url : '/deleteFile',
				data : {fileName : targetFile, type : type},
				dataType : 'text',
				type : 'POST',
				success : function(result){
					alert(result);
					targetLi.remove();
				}
			});
		}
	});

	function findClosestLi(element) {
		while (element && element.tagName !== "LI") {
			element = element.parentNode;
		}
		return element;
	}
</script>
<%@include file="../includes/footer.jsp" %>