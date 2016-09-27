/**
 *
 * @param scene  THREE.Scene().
 * @param camera THREE.camera().
 * @param domElement
 * @constructor
 * @author chenjy / http://chenjy1225.github.io/
 * @author email / chenjy1225@163.com/
 */

THREE.FirstLockControl = function (scene, camera, domElement) {

    var fristPerson;
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

    var helper = new THREE.Object3D();

    var controlEnabled = false;


    var flc = this;

    this.scene = scene;
    this.camera = camera;
    this.domElement = ( domElement !== undefined ) ? domElement : document;
    this.moveSpeed = 200;
    this.lookSpeed = 0.2;
    this.enabled = false;

    this.initLockControl = function () {

        var _block = document.createElement("div");
        _block.setAttribute("id", "blocker");
        document.body.appendChild(_block);

        var _instructions = document.createElement("div");
        _instructions.setAttribute("id", "instructions");
        _block.appendChild(_instructions);

        var _title = document.createElement("span");
        _title.innerHTML = "Click to play";
        _instructions.appendChild(_title);

        var _havePointerLock = 'pointerLockElement' in document || 'mozPointerLockElement' in document || 'webkitPointerLockElement' in document;

        if (_havePointerLock) {
            var _element = document.body;
            var _pointerlockchange = function (event) {
                if (document.pointerLockElement === _element || document.mozPointerLockElement === _element || document.webkitPointerLockElement === _element) {
                    flc.enabled = true;
                    controlEnabled = true;
                    _block.style.display = 'none';
                } else {
                    flc.enabled = false;
                    controlEnabled = false;
                    _block.style.display = 'block';
                    _instructions.style.display = '';
                }
            }
            var _pointerlockerror = function (event) {
                _instructions.style.display = '';
            }
            document.addEventListener('pointerlockchange', _pointerlockchange, false);
            document.addEventListener('mozpointerlockchange', _pointerlockchange, false);
            document.addEventListener('webkitpointerlockchange', _pointerlockchange, false);

            document.addEventListener('pointerlockerror', _pointerlockerror, false);
            document.addEventListener('mozpointerlockerror', _pointerlockerror, false);
            document.addEventListener('webkitpointerlockerror', _pointerlockerror, false);
            _instructions.addEventListener('click', function (event) {
                _instructions.style.display = 'none';
                _element.requestPointerLock = _element.requestPointerLock || _element.mozRequestPointerLock || _element.webkitRequestPointerLock;
                if (/Firefox/i.test(navigator.userAgent)) {
                    var _fullscreenchange = function (event) {
                        if (document.fullscreenElement === _element || document.mozFullscreenElement === _element || document.mozFullScreenElement === _element) {

                            document.removeEventListener('fullscreenchange', _fullscreenchange);
                            document.removeEventListener('mozfullscreenchange', _fullscreenchange);

                            _element.requestPointerLock();
                        }

                    };
                    document.addEventListener('fullscreenchange', _fullscreenchange, false);
                    document.addEventListener('mozfullscreenchange', _fullscreenchange, false);

                    _element.requestFullscreen = _element.requestFullscreen || _element.mozRequestFullscreen || _element.mozRequestFullScreen || _element.webkitRequestFullscreen;

                    _element.requestFullscreen();

                } else {
                    _element.requestPointerLock();
                }

            }, false);
        } else {
            _instructions.innerHTML = 'Your browser doesn\'t seem to support Pointer Lock API';
        }


        fristPerson = new Physijs.BoxMesh(new THREE.CubeGeometry(5, 5, 5), Physijs.createMaterial(new THREE.MeshPhongMaterial({
            opacity: 0,
            transblock: true
        })), 1);
        fristPerson.position.set(100, 2, 100);
        scene.add(fristPerson);
    }

    this.keyDown = function (event) {

        if (!/65|68|83|87|37|38|39|40/.test(event.keyCode)) {
            return
        }
        if (event.keyCode === 87 || event.keyCode === 38) {
            state.front = true;
            state.back = false;
            wdown = true;
        } else if (event.keyCode === 83 || event.keyCode === 40) {
            state.back = true;
            state.front = false;
            adown = true;
        } else if (event.keyCode === 65 || event.keyCode === 37) {
            state.left = true;
            state.right = false;
            sdown = true;
        } else if (event.keyCode === 68 || event.keyCode === 39) {
            state.right = true;
            state.left = false;
            ddown = true;
        }
        if (state.front || state.back || state.left || state.right) {
            state.moving = true;
        }
    }

    this.keyUp = function (event) {
        if (!/65|68|83|87|37|38|39|40/.test(event.keyCode)) {
            return
        }
        if (event.keyCode === 87 || event.keyCode === 38) {
            state.front = false;
            wdown = false;
        } else if (event.keyCode === 83 || event.keyCode === 40) {
            state.back = false;
            adown = false;
        } else if (event.keyCode === 65 || event.keyCode === 37) {
            state.left = false;
            sdown = false;
        } else if (event.keyCode === 68 || event.keyCode === 39) {
            state.right = false;
            ddown = false;
        }
        if (!state.front && !state.back && !state.left && !state.right) {
            state.moving = false;
        }
    }

    this.mouseMove = function (event) {

        if (flc.enabled === false) return;

        var _movementx = event.movementX;
        var _movementy = event.movementY;

        helper.rotation.y += _movementx;
        helper.rotation.x += _movementy * flc.lookSpeed;

        helper.position.x -= _movementx * flc.lookSpeed;
        helper.position.y += _movementy;

        if (helper.position.y > 150) {
            helper.position.y = 150;
        }
        if (helper.position.y < -150) {
            helper.position.y = -150;
        }
        state.angle = (helper.position.x / 2) % 360;
    }

    this.update = function () {
        if (controlEnabled === false) return;

        var _direction;

        flc.camera.position.x = fristPerson.position.x + distance * Math.sin((helper.position.x) * Math.PI / 360);
        flc.camera.position.z = fristPerson.position.z + distance * Math.cos((helper.position.x) * Math.PI / 360);
        flc.camera.position.y = fristPerson.position.y + distance * Math.sin((helper.position.y) * Math.PI / 360);
        flc.camera.position.y += 1;
        flc.camera.lookAt(fristPerson.position);

        if (state.moving) {
            _direction = state.angle;
            if (state.front && !state.left && !state.back && !state.right) {
                _direction += 0;
            }
            if (state.front && state.left && !state.back && !state.right) {
                _direction += 45;
            }
            if (!state.front && state.left && !state.back && !state.right) {
                _direction += 90;
            }
            if (!state.front && state.left && state.back && !state.right) {
                _direction += 135;
            }
            if (!state.front && !state.left && state.back && !state.right) {
                _direction += 180;
            }
            if (!state.front && !state.left && state.back && state.right) {
                _direction += 225;
            }
            if (!state.front && !state.left && !state.back && state.right) {
                _direction += 270;
            }
            if (state.front && !state.left && !state.back && state.right) {
                _direction += 315;
            }
            velocity.x = -Math.sin(_direction * Math.PI / 180) * flc.moveSpeed;
            velocity.z = -Math.cos(_direction * Math.PI / 180) * flc.moveSpeed;
            fristPerson.setLinearVelocity(velocity);
            flc.scene.simulate(undefined, 1);
        }
    }

    document.addEventListener('keydown', this.keyDown, false);
    document.addEventListener('keyup', this.keyUp, false);
    flc.domElement.addEventListener('mousemove', this.mouseMove, false);


    this.initLockControl();
}