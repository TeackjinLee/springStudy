<div class="bigPictureWrapper">
            	<div class='bigPicture'>
            	</div>
            </div>
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
            		<div class="panel panel-default">
            			<div class="panel-heading">Files</div>
            			<!-- /.panel-heading -->
            			<div class='panel-body'>
            				<div class='uploadResult'>
            					<ul>
            					</ul>
            				</div>
            			</div>
            		</div>
            	</div>
            </div>