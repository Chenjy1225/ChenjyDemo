var FirstLockControl = function(camera) {

	var FLC = this;

	this.moveSpeed = 200;
	this.lookSpeed = 0.2;
	this.camera = camera;
	this.enabled = false;

	var state = {
		front: false,
		back: false,
		left: false,
		right: false,
		moving: false,
		angle: 0
	};
	var velocity = {
		x: 0,
		y: 0,
		z: 0
	};
	var wdown = false,
		adown = false,
		sdown = false,
		ddown = false;
	var distance = 2;

	var help = new THREE.Object3D();

	var controlEnabled = false;

	var fristPerson = new Physijs.BoxMesh(new THREE.CubeGeometry(5, 5, 5), Physijs.createMaterial(new THREE.MeshPhongMaterial({
		opacity: 0,
		transblock: true
	})), 1);
	fristPerson.position.set(100, 2, 100);
	scene.add(fristPerson);

	var block = document.createElement("div");
	block.setAttribute("id", "blocker");
	document.body.appendChild(block);

	var instructions = document.createElement("div");
	instructions.setAttribute("id", "instructions");
	block.appendChild(instructions);

	var title = document.createElement("span");
	title.innerHTML = "Click to play";
	instructions.appendChild(title);

	var havePointerLock = 'pointerLockElement' in document || 'mozPointerLockElement' in document || 'webkitPointerLockElement' in document;

	if(havePointerLock) {
		var element = document.body;
		var pointerlockchange = function(event) {
			if(document.pointerLockElement === element || document.mozPointerLockElement === element || document.webkitPointerLockElement === element) {
				FLC.enabled = true;
				controlEnabled = true;
				block.style.display = 'none';
			} else {
				FLC.enabled = false;
				controlEnabled = false;
				block.style.display = 'block';
				instructions.style.display = '';
			}
		}
		var pointerlockerror = function(event) {
			instructions.style.display = '';
		}
		document.addEventListener('pointerlockchange', pointerlockchange, false);
		document.addEventListener('mozpointerlockchange', pointerlockchange, false);
		document.addEventListener('webkitpointerlockchange', pointerlockchange, false);

		document.addEventListener('pointerlockerror', pointerlockerror, false);
		document.addEventListener('mozpointerlockerror', pointerlockerror, false);
		document.addEventListener('webkitpointerlockerror', pointerlockerror, false);
		instructions.addEventListener('click', function(event) {
			instructions.style.display = 'none';
			element.requestPointerLock = element.requestPointerLock || element.mozRequestPointerLock || element.webkitRequestPointerLock;
			if(/Firefox/i.test(navigator.userAgent)) {
				var fullscreenchange = function(event) {
					if(document.fullscreenElement === element || document.mozFullscreenElement === element || document.mozFullScreenElement === element) {

						document.removeEventListener('fullscreenchange', fullscreenchange);
						document.removeEventListener('mozfullscreenchange', fullscreenchange);

						element.requestPointerLock();
					}

				};
				document.addEventListener('fullscreenchange', fullscreenchange, false);
				document.addEventListener('mozfullscreenchange', fullscreenchange, false);

				element.requestFullscreen = element.requestFullscreen || element.mozRequestFullscreen || element.mozRequestFullScreen || element.webkitRequestFullscreen;

				element.requestFullscreen();

			} else {
				element.requestPointerLock();
			}

		}, false);
	} else {
		instructions.innerHTML = 'Your browser doesn\'t seem to support Pointer Lock API';
	}

	var onKeyDown = function(event) {
		if(!/65|68|83|87|37|38|39|40/.test(event.keyCode)) {
			return
		}
		if(event.keyCode === 87 || event.keyCode === 38) {
			state.front = true;
			state.back = false;
			wdown = true;
		} else if(event.keyCode === 83 || event.keyCode === 40) {
			state.back = true;
			state.front = false;
			adown = true;
		} else if(event.keyCode === 65 || event.keyCode === 37) {
			state.left = true;
			state.right = false;
			sdown = true;
		} else if(event.keyCode === 68 || event.keyCode === 39) {
			state.right = true;
			state.left = false;
			ddown = true;
		}
		if(state.front || state.back || state.left || state.right) {
			state.moving = true;
		}
	}

	var onKeyUp = function(event) {
		if(!/65|68|83|87|37|38|39|40/.test(event.keyCode)) {
			return
		}
		if(event.keyCode === 87 || event.keyCode === 38) {
			state.front = false;
			wdown = false;
		} else if(event.keyCode === 83 || event.keyCode === 40) {
			state.back = false;
			adown = false;
		} else if(event.keyCode === 65 || event.keyCode === 37) {
			state.left = false;
			sdown = false;
		} else if(event.keyCode === 68 || event.keyCode === 39) {
			state.right = false;
			ddown = false;
		}
		if(!state.front && !state.back && !state.left && !state.right) {
			state.moving = false;
		}
	}

	var onMouseMove = function(event) {

		if(FLC.enabled === false) return;

		var movementX = event.movementX;
		var movementY = event.movementY;

		help.rotation.y += movementX;
		help.rotation.x += movementY * FLC.lookSpeed;

		help.position.x -= movementX * FLC.lookSpeed;
		help.position.y += movementY;

		if(help.position.y > 150) {
			help.position.y = 150;
		}
		if(help.position.y < -150) {
			help.position.y = -150;
		}
		state.angle = (help.position.x / 2) % 360;
	}

	document.addEventListener('keydown', onKeyDown, false);
	document.addEventListener('keyup', onKeyUp, false);
	document.addEventListener('mousemove', onMouseMove, false);

	this.move = function(event) {
		if(controlEnabled === false) return;

		var direction;

		camera.position.x = fristPerson.position.x + distance * Math.sin((help.position.x) * Math.PI / 360);
		camera.position.z = fristPerson.position.z + distance * Math.cos((help.position.x) * Math.PI / 360);
		camera.position.y = fristPerson.position.y + distance * Math.sin((help.position.y) * Math.PI / 360);
		camera.position.y += 1;
		camera.lookAt(fristPerson.position);

		if(state.moving) {
			direction = state.angle;
			if(state.front && !state.left && !state.back && !state.right) {
				direction += 0;
			}
			if(state.front && state.left && !state.back && !state.right) {
				direction += 45;
			}
			if(!state.front && state.left && !state.back && !state.right) {
				direction += 90;
			}
			if(!state.front && state.left && state.back && !state.right) {
				direction += 135;
			}
			if(!state.front && !state.left && state.back && !state.right) {
				direction += 180;
			}
			if(!state.front && !state.left && state.back && state.right) {
				direction += 225;
			}
			if(!state.front && !state.left && !state.back && state.right) {
				direction += 270;
			}
			if(state.front && !state.left && !state.back && state.right) {
				direction += 315;
			}
			velocity.x = -Math.sin(direction * Math.PI / 180) * FLC.moveSpeed;
			velocity.z = -Math.cos(direction * Math.PI / 180) * FLC.moveSpeed;
			fristPerson.setLinearVelocity(velocity);
			scene.simulate(undefined, 1);
		}
	}
}