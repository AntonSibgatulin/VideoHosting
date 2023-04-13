(function($){
	
	$.fn.youTubeEmbed = function(settings){
		
		// Установка может быть либо строкой URL,
		// либо объектом
		
		if(typeof settings == 'string'){
			settings = {'video' : settings}
		}
		
		// Значения по умолчанию
		
		var def = {
			width		: 640,
			progressBar	: true
		};
		
		settings = $.extend(def,settings);
		
		var elements = {
			originalDIV	: this,	// "this" oплагина
			container	: null,	// Контейнер div, вставляемый плагином
			control		: null,	// Кнопка старт/стоп 
			player		: null,	// Проигрыватель flash
			progress	: null,	// Индикатор прогресса
			elapsed		: null	// Светлоголубой цвет для полоски прогресса
		};
		

		try{	

			settings.videoID = settings.video.match(/v=(.{11})/)[1];
			
			// safeID - это обнаженная версия videoID,
			// которая готова к использованию как имя функции

			settings.safeID = settings.videoID.replace(/[^a-z0-9]/ig,'');
		
		} catch (e){
			// Если url ошибочный, просто возвращаем "this"
			return elements.originalDIV;
		}

		// Достаем данные о видео из API YouTube

		var youtubeAPI = 'http://gdata.youtube.com/feeds/api/videos?v=2&alt=jsonc';

		$.get(youtubeAPI,{'q':settings.videoID},function(response){
			
			var data = response.data;
	
			if(!data.totalItems || data.items[0].accessControl.embed!="allowed"){
				
				// Если видео не найдено, или встраивание запрещено;
				
				return elements.originalDIV;
			}

			// data содержит информацию API о видео:
			
			data = data.items[0];
			
			settings.ratio = 3/4;
			if(data.aspectRatio == "widescreen"){
				settings.ratio = 9/16;
			}
			
			settings.height = Math.round(settings.width*settings.ratio);

			// Создаем контейнер внутри оригинального div, 
			// который будет содержать объект/код встраивания видео

			elements.container = $('<div>',{className:'flashContainer',css:{
				width	: settings.width,
				height	: settings.height
			}}).appendTo(elements.originalDIV);

			// Встраивание проигрывателя Chromeless YouTube
			// и загрузка видео в него:

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

			// Используем get, потому что нам нужен элемент DOM, а не объект jquery:
			
			elements.player = elements.container.flash().get(0);

			// Создаем Div управления. Он будет действовать как кнопка старт/пауза

			elements.control = $('<div>',{className:'controlDiv play'})
							   .appendTo(elements.container);

			// Если пользователь хочет показать индикатор прогресса:

			if(settings.progressBar){
				elements.progress =	$('<div>',{className:'progressBar'})
									.appendTo(elements.container);

				elements.elapsed =	$('<div>',{className:'elapsed'})
									.appendTo(elements.progress);
				
				elements.progress.click(function(e){
					
					// Когда происходит нажатие кнопки мыши на индикаторе прогресса,
					// ищем соответствующий момент видео.
					
					var ratio = (e.pageX-elements.progress.offset().left)/elements.progress.outerWidth();
					
					elements.elapsed.width(ratio*100+'%');
					elements.player.seekTo(Math.round(data.duration*ratio), true);
					return false;
				});

			}

			var initialized = false;
			
			// Создание глобальной функции получения события для видео
			// (требуется API проигрывателя YouTube):
			
			window['eventListener_'+settings.safeID] = function(status){

				var interval;
				
				if(status==-1)	// видео загружено
				{
					if(!initialized)
					{
						// Ожидаем нажатия на кнопку управления:
						
						elements.control.click(function(){
							if(!elements.container.hasClass('playing')){
								
								// Если видео еще не проигрывается, запускаем его:

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
								
								// Если видео проигрывается, приостанавливаем его:
								
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
						// Это происходит если пользователь нажал на лого
						// YouTube и должен быть перенаправлен на youtube.com

						if(elements.container.hasClass('playing'))
						{
							elements.control.click();
						}
					}
				}
				
				if(status==0){ // видео закончилось
					elements.control.removeClass('pause').addClass('replay');
					elements.container.removeClass('playing');
				}
			}
			
			// Данная глобальная функция вызывается, когда проигрыватель загружен.
			// Она доступна всем видео на странице:
			
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