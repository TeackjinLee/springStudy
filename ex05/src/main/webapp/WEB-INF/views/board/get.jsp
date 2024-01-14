<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../includes/header.jsp" %>
	<div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Board Read Page</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Board Read Page
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                       		<div class="form-group">
                       			<label>Bno</label>
                       			<input class="form-control" name="bno"
                       				value='<c:out value="${board.bno}"/>' readonly="readonly">
                       		</div>
                       		<div class="form-group">
                       			<label>Title</label>
                       			<input class="form-control" name="title"
                       				value='<c:out value="${board.title}"/>' readonly="readonly">
                       		</div>
                       		<div class="form-group">
                       			<label>Text area</label>
                       			<textarea class="form-control" rows="3" name="content" readonly="readonly">
                       			<c:out value="${board.content}" /></textarea>
                       		</div>
                       		<div class="form-group">
                       			<label>Writer</label>
                       			<input class="form-control" name="writer" value='<c:out value="${board.writer}"/>' readonly="readonly" />
                       		</div>
                       		<button data-oper='modify' class="btn btn-default">Modify</button>
                       		<button data-oper='list' class="btn btn-info">List</button>
                  			<form id='operForm' action="/board/modify" method="get">
                  				<input type='hidden' id='bno' name='bno' value='<c:out value="${board.bno}"/>'>
                  				<input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}" />'>
                  				<input type='hidden' name='amount' value='<c:out value="${cri.amount}" />'>
                  				<input type='hidden' name='keyword' value='<c:out value="${cri.keyword}" />'>
                  				<input type='hidden' name='type' value='<c:out value="${cri.type}" />'>
                  			</form>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
			
			<%@include file="../includes/bigPicture.jsp" %>
			
			<div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <!-- <div class="panel-heading">
                            <i class="fa fa-comments fa-fw"></i> Reply
                        </div> -->
						<div class="panel-heading">
							<i class="fa fa-comments fa-fw"></i> Reply
							<button id='addReplyBtn' class="btn btn-primary btn-xs pull-right">New Reply</button>
						</div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                       		<ul class="chat">
                       			<!-- start reply -->
                       			<li class="left clearfix" data-rno='12'>
                       				<div>
                       					<div class="header">
                       						<strong class="primary-font">user00</strong>
                       						<small class="pull-right text-muted">2024-01-03 15:03</small>
                       					</div>
                       					<div>Good job!</div>
                       				</div>
                       			</li>
                       		</ul>
                        </div>
                        <!-- /.panel-body -->
						<div class="panel-footer">
						</div>
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->

			<!-- Modal -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
						</div>
						<div class="modal-body">
							<div class="from-group">
								<label>Reply</label>
								<input class="form-control" name="reply" value="New Reply!!!!">
							</div>
							<div class="form-group">
								<label>Replyer</label>
								<input name="replyer" class="form-control" value="replyer">
							</div>
							<div class="form-group">
								<label>Reply Date</label>
								<input name="replyDate" class="form-control" value="">
							</div>
						</div>
						<div class="modal-footer">
							<button id="modalModBtn" type="button" class="btn btn-warning">Modify</button>
							<button id="modalRemoveBtn" type="button" class="btn btn-danger">Remove</button>
							<button id="modalRegisterBtn" type="butoon" class="btn btn-primary" data-dismiss="modal">Register</button>
							<button id="modalCloseBtn" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							<button id="modalClassBtn" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						</div>
					</div>
				</div>
			</div> 
			<script type="text/javascript" src="/resources/js/reply.js"></script>
            <script type="text/javascript">
            
            	
				console.log("======================");
				console.log("JS TEST");

				var bnoValue = '<c:out value="${board.bno}"/>';
				
				var replyUL = document.querySelector('.chat');
				showList(1);

				function showList(page) {
					console.log("show list " + page);

					replyService.getList({bno:bnoValue, page:page||1}, function(replyCnt, list){
						console.log("replyCnt : " + replyCnt);
						console.log("list : " + list);
						console.log(list);

						if(page == -1) {
							pageNum = Math.ceil(replyCnt/10.0);
							showList(pageNum);
							return;
						}

						var str = "";
						if(list == null || list.length == 0) {
							replyUL.innerHTML = "";
							return;
						}
						for (var i=0, len = list.length || 0; i<len; i++) {
							str += "<li class='left clearfix' data-rno='" + list[i].rno + "'>";
							str += "<div><div class='header'><strong class='primary-font'>" + list[i].replyer + "</strong>";
							str += "<small class='pull-right text-muted'>" + replyService.displayTime(list[i].replyDate) + "</small></div>";
							str += "<p>" + list[i].reply + "</p></div></li>";
						}
						replyUL.innerHTML = str;
						showReplyPage(replyCnt);
					});
				}

				$(document).ready(function(){
					var modal = $(".modal");
					var modalInputReply = modal.find("input[name='reply']");
					var modalInputReplyer = modal.find("input[name='replyer']");
					var modalInputReplyDate = modal.find("input[name='replyDate']");

					var modalModBtn = $("#modalModBtn");
					var modalRemoveBtn = $("#modalRemoveBtn");
					var modalRegisterBtn = $("#modalRegisterBtn");

					$("#addReplyBtn").on("click", function(e){
						modal.find("input").val("");
						modalInputReplyDate.closest('div').hide();
						modal.find("button[id != 'modalCloseBtn']").hide();

						modalRegisterBtn.show();
						$(".modal").modal("show");
					});

					modalRegisterBtn.on("click", function(e){
						var reply = {
							reply: modalInputReply.val(),
							replyer:modalInputReplyer.val(),
							bno:bnoValue
						};
						replyService.add(reply, function(result){
							alert(result);
							modal.find("input").val("");
							modal.modal("hide");

							// showList(1);
							showList(-1);
						});
					});
					
					$(".chat").on("click", "li", function(e) {
						var rno = $(this).data("rno");
						console.log(rno);
						replyService.get(rno, function(reply){
							modalInputReply.val(reply.reply);
							modalInputReplyer.val(reply.replyer);
							modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly", "readonly");
							modal.data("rno", reply.rno);

							modal.find("button[id != 'modalCloseBtn']").hide();
							modalModBtn.show();
							modalRemoveBtn.show();

							$(".modal").modal("show");
						});
					});

					modalModBtn.on("click", function(e){
						var reply = {rno:modal.data("rno"), reply:modalInputReply.val()};

						replyService.update(reply, function(result){
							alert(result);
							modal.modal("hide");
							showList(pageNum);
						});
					});

					modalRemoveBtn.on("click", function(e){
						var rno = modal.data("rno");

						replyService.remove(rno, function(result){
							alert(result);
							modal.modal("hide");
							showList(pageNum);
						});
					});
				});

				// let modal = document.querySelector('.modal');
				// let modalInputReply = document.getElementsByName('reply')[0];
				// let modalInputReplyer = document.getElementsByName('replyer')[0];
				// let modalInputReplyDate = document.getElementsByName('replyDate')[0];

				// let modalModBtn = document.getElementById('modalModBtn');
				// let modalRemoveBtn = document.getElementById('modalRemoveBtn');
				// let modalRegisterBtn = document.getElementById('modalRegisterBtn');

				// let modal = document.querySelector('.modal');
				// let modalInputReply = document.getElementsByName('reply')[0];
				// let modalInputReplyer = document.getElementsByName('replyer')[0];
				// let modalInputReplyDate = document.getElementsByName('replyDate')[0];

				// let modalModBtn = document.getElementById('modalModBtn');
				// let modalRemoveBtn = document.getElementById('modalRemoveBtn');
				// let modalRegisterBtn = document.getElementById('modalRegisterBtn');

				// let addReplyBtn = document.getElementById('addReplyBtn');
				// addReplyBtn.addEventListener("click", function(e){
				// 	var inputModalList = modal.querySelectorAll("input");
				// 	inputModalList.forEach(function(input){
				// 		input.value = "";
				// 	});

				// 	// 특정 버튼 숨기기
				// 	var closestDiv = modalInputReplyDate.closest('div');
				
				// 	closestDiv.style.display = "none";
					
				// 	modal.classList.add("show");

				// 	// 특정 버튼 숨기기
				// 	// var buttonsToHide = modal.querySelectorAll("button[id!='modalCloseBtn']");
				// 	// buttonsToHide.forEach(function(button) {
				// 	// 	button.style.display = "none";
				// 	// });

					
				// 	modalRegisterBtn.style.display = "block";
			

				// 	modal.classList.add("show");
				// });
				
				// for replyService add test
				// replyService.add(
				// 	{reply:"JS Test", replyer:"tester", bno:bnoValue},
				// 	function(result) {
				// 		alert("RESULT : " + result);
				// 	}
				// );
				
				// reply List Test
				/* replyService.getList({bno:bnoValue, page:1}, function(list){
					
					for(var i=0, len = list.length||0; i<len; i++) {
						console.log(list[i]);
					}
				}); */

				// 23번 댓글 삭제 테스트
				// replyService.remove(18, function(count){
				// 	console.log(count);
				// 	if(count === "success") {
				// 		alert("REMOVED");
				// 	}
				// }, function(err){
				// 	alert('ERROR...');
				// });

				//22번 댓글 수정
				// replyService.update({
				// 	rno : 22,
				// 	bno : bnoValue,
				// 	reply : "Modified Reply......"
				// }, function(result){
				// 	alert("수정 완료......");
				// });

				// replyService.get(22, function(data){
				// 	console.log(data);
				// });

				const operForm = document.getElementById('operForm');
				const button_modify = document.getElementsByTagName('button')[2];
				const button_list = document.getElementsByTagName('button')[3];
				
				button_modify.addEventListener("click", function(e) {
				    operForm.setAttribute('action', '/board/modify');
				    operForm.submit();
				});
				
				button_list.addEventListener("click", function(e) {
				    operForm.setAttribute('action', '/board/list');
				    operForm.submit();
				});
				
				// page
				var pageNum = 1;
				var replyPageFooter = document.querySelector('.panel-footer');

				function showReplyPage(replyCnt) {
					var endNum = Math.ceil(pageNum/10.0) * 10;
					var startNum = endNum -9;

					var prev = startNum != 1;
					var next = false;
					
					if (endNum * 10 >= replyCnt) {
						endNum = Math.ceil(replyCnt/10.0);
					};

					if (endNum * 10 < replyCnt) {
						next = true;
					};
					var str = "<ul class='pagination pull-right'>";
					if (prev) {
						str += "<li class='page-item'><a class='page-link' href='"+(strtNum -1) +"'>Previous</a></li>";
					}

					for (var i=startNum; i<=endNum; i++) {
						var active = pageNum == i ? "active" : "";
						str += "<li class='page-item " + active + " '><a class='page-link' href='" + i + "'>" + i + "</a></li>";
					}

					if(next) {
						str += "<li class='page-item'><a class='page-link' href='" + (endNum + 1) + "'>Next</a></li>";
					}

					str += "</ul></div>"

					console.log(str);
					replyPageFooter.innerHTML = str;
				}
				
				replyPageFooter.addEventListener("click", function(e){
					console.log("click");
					console.log(e.target);
					console.log(e.target.tagName.toLowerCase() === "a" && e.target.parentElement.tagName.toLowerCase() === "li");
					if (e.target.tagName.toLowerCase() === "a" && e.target.parentElement.tagName.toLowerCase() === "li") {
						console.log("123");
						e.preventDefault();
						console.log("page click");

						// href 속성에서 페이지 번호 가져오기
						var targetPageNum = e.target.getAttribute('href');
						console.log("targetPageNum : " + targetPageNum);
						pageNum = targetPageNum;
						showList(pageNum);
					}
				});
				
				(function(){
            		fetch("/board/getAttachList?bno=" + bnoValue)
            		.then(response => response.json())
            		.then(arr => {
            			console.log(arr);
            			var str = "";
            			var state = {items : [...arr]};
						state.items.forEach(function(attach, i){
							console.log("##############");
							console.log(attach);
							console.log(i);
							//image type
							if (attach.fileType) {
								var fileCallPath = encodeURIComponent(attach.uploadPath + "/s_" + attach.uuid + "_" + attach.fileName);

								str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "'><div>";
								str += "<img src='/display?fileName=" + fileCallPath + "' >";
								str += "</div></li>";
							} else {
								var fileCallPath = encodeURIComponent(attach.uploadPath + "/" + attach.uuid + "_" + attach.fileName);

								str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "'><div>";
								str += "<span > " + attach.fileName + " </span><br />";
								str += "<img src='/resources/img/attach.png'>";
								str += "</div></li>";
							}

							const uploadResultUl = document.querySelector(".uploadResult ul");
							uploadResultUl.innerHTML = str;
						});
            			
            		})
            		.catch(error => console.error("Error fetching data : ", error));
            	})();
				
				// 26.2.3 첨부파일 클릭시 이벤트 처리
				let uploadResult = document.querySelector(".uploadResult");
				uploadResult.addEventListener("click", function(e){
					var liElement = e.target.closest('li');
					if (liElement && liElement.tagName === "LI") {
				        var liObj = liElement;
				        var path = liObj.dataset.path + "/" + liObj.dataset.uuid + "_" + liObj.dataset.filename;
				        path = path.replace(/\\/g, "/");
				        path = path.split('/').map(encodeURIComponent).join('/');
				     	// 디코딩
				        path = decodeURIComponent(path);
						if (liObj.dataset.type === "true") {
							showImage(path);
						} else {
							window.location.href = "/download?fileName=" + path;
						}
					}
				});

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
					
					// 원본 이미지 창 닫기
					bigPictureWrapper.addEventListener("click", function(e) {
						animateElement(imgElement, animationHideOptions, 1000);
						setTimeout(function(){
							bigPictureWrapper.style.display = "none";
						}, 1000);
					});
				}
				
				function animateElement(element, options, duration) {
					element.style.transition = "all " + duration / 1000 + "s";
					Object.keys(options).forEach(function (property) {
						element.style[property] = options[property];
					});
				}
			</script>
<%@include file="../includes/footer.jsp" %>