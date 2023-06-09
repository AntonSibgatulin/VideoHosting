(function($){
	
	$.fn.youTubeEmbed = function(settings){
		
		// ��������� ����� ���� ���� ������� URL,
		// ���� ��������
		
		if(typeof settings == 'string'){
			settings = {'video' : settings}
		}
		
		// �������� �� ���������
		
		var def = {
			width		: 640,
			progressBar	: true
		};
		
		settings = $.extend(def,settings);
		
		var elements = {
			originalDIV	: this,	// "this" o�������
			container	: null,	// ��������� div, ����������� ��������
			control		: null,	// ������ �����/���� 
			player		: null,	// ������������� flash
			progress	: null,	// ��������� ���������
			elapsed		: null	// ������������� ���� ��� ������� ���������
		};
		

		try{	

			settings.videoID = settings.video.match(/v=(.{11})/)[1];
			
			// safeID - ��� ���������� ������ videoID,
			// ������� ������ � ������������� ��� ��� �������

			settings.safeID = settings.videoID.replace(/[^a-z0-9]/ig,'');
		
		} catch (e){
			// ���� url ���������, ������ ���������� "this"
			return elements.originalDIV;
		}

		// ������� ������ � ����� �� API YouTube

		var youtubeAPI = 'http://gdata.youtube.com/feeds/api/videos?v=2&alt=jsonc';

		$.get(youtubeAPI,{'q':settings.videoID},function(response){
			
			var data = response.data;
	
			if(!data.totalItems || data.items[0].accessControl.embed!="allowed"){
				
				// ���� ����� �� �������, ��� ����������� ���������;
				
				return elements.originalDIV;
			}

			// data �������� ���������� API � �����:
			
			data = data.items[0];
			
			settings.ratio = 3/4;
			if(data.aspectRatio == "widescreen"){
				settings.ratio = 9/16;
			}
			
			settings.height = Math.round(settings.width*settings.ratio);

			// ������� ��������� ������ ������������� div, 
			// ������� ����� ��������� ������/��� ����������� �����

			elements.container = $('<div>',{className:'flashContainer',css:{
				width	: settings.width,
				height	: settings.height
			}}).appendTo(elements.originalDIV);

			// ����������� ������������� Chromeless YouTube
			// � �������� ����� � ����:

			elements.container.flash({
				swf			: 'http://www.youtube.com/apiplayer?enablejsapi=1&version=3',
				id			: 'video_'+settings.safeID,
				height		: settings.height,
				width		: settings.width,
				allowScriptAccess:'always',
				wmode		: 'transparent',
				flashvars	: {
					"video_id"		: settings.videoID,
					"playerapiid"	: settings.safeID
				}
			});

			// ���������� get, ������ ��� ��� ����� ������� DOM, � �� ������ jquery:
			
			elements.player = elements.container.flash().get(0);

			// ������� Div ����������. �� ����� ����������� ��� ������ �����/�����

			elements.control = $('<div>',{className:'controlDiv play'})
							   .appendTo(elements.container);

			// ���� ������������ ����� �������� ��������� ���������:

			if(settings.progressBar){
				elements.progress =	$('<div>',{className:'progressBar'})
									.appendTo(elements.container);

				elements.elapsed =	$('<div>',{className:'elapsed'})
									.appendTo(elements.progress);
				
				elements.progress.click(function(e){
					
					// ����� ���������� ������� ������ ���� �� ���������� ���������,
					// ���� ��������������� ������ �����.
					
					var ratio = (e.pageX-elements.progress.offset().left)/elements.progress.outerWidth();
					
					elements.elapsed.width(ratio*100+'%');
					elements.player.seekTo(Math.round(data.duration*ratio), true);
					return false;
				});

			}

			var initialized = false;
			
			// �������� ���������� ������� ��������� ������� ��� �����
			// (��������� API ������������� YouTube):
			
			window['eventListener_'+settings.safeID] = function(status){

				var interval;
				
				if(status==-1)	// ����� ���������
				{
					if(!initialized)
					{
						// ������� ������� �� ������ ����������:
						
						elements.control.click(function(){
							if(!elements.container.hasClass('playing')){
								
								// ���� ����� ��� �� �������������, ��������� ���:

								elements.control.removeClass('play replay').addClass('pause');
								elements.container.addClass('playing');
								elements.player.playVideo();
								
								if(settings.progressBar){
									interval = window.setInterval(function(){
										elements.elapsed.width(
											((elements.player.getCurrentTime()/data.duration)*100)+'%'
										);
									},1000);
								}
								
							} else {
								
								// ���� ����� �������������, ���������������� ���:
								
								elements.control.removeClass('pause').addClass('play');
								elements.container.removeClass('playing');
								elements.player.pauseVideo();
								
								if(settings.progressBar){
									window.clearInterval(interval);
								}
							}
						});
						
						initialized = true;
					}
					else{
						// ��� ���������� ���� ������������ ����� �� ����
						// YouTube � ������ ���� ������������� �� youtube.com

						if(elements.container.hasClass('playing'))
						{
							elements.control.click();
						}
					}
				}
				
				if(status==0){ // ����� �����������
					elements.control.removeClass('pause').addClass('replay');
					elements.container.removeClass('playing');
				}
			}
			
			// ������ ���������� ������� ����������, ����� ������������� ��������.
			// ��� �������� ���� ����� �� ��������:
			
			if(!window.onYouTubePlayerReady)
			{				
				window.onYouTubePlayerReady = function(playerID){
					document.getElementById('video_'+playerID).addEventListener('onStateChange','eventListener_'+playerID);
				}
			}
		},'jsonp');

		return elements.originalDIV;
	}

})(jQuery);