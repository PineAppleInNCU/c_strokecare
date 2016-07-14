		var canvas;
		var context;
		var ball;

		var prevX = 150;
		var prevY = 150;
		var offSet = 0.05;

		var accelTimer;

		function onAccelerometerload(){
			canvas = document.getElementById("canvas");
			context = canvas.getContext("2d");

			ball = document.getElementById("ball");
			ball.onload = function(){
				var options = { frequency: 100};
				accelTimer = navigator.accelerometer.watchAcceleration(
					moveBall, stopBall, options);
			};
			ball.src = "css/images/ball.png";
		}

		function moveBall(acceleration){
			var x = acceleration.x*100;
			var y = acceleration.y*100;

			var newX = x*offset+(1 - offset)*prevX;
			var newY = y*offset+(1 - offset)*prevY;

			prevX = newX;
			prevY = newY;

			drawImage(newX,newY);
		}

		function stopBall(error){
			navigator.accelerometer.clearWatch(accelTimer);
			alert("erroe detecting acceleration");
		}

		function drawImage(x,y){
			context.clearRect(0,0,350,350);
			context.drawImage(ball,0,0,100,81,x,y,100,81);
		}