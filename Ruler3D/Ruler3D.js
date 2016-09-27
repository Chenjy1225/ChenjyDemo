/**
 *
 * @param scene  THREE.Scene().
 * @param camera THREE.camera().
 * @param sizes  The size of coordinate system default 1000.
 * @oaram doElement
 * @constructor
 * @author chenjy / http://chenjy1225.github.io/
 * @author email / chenjy1225@163.com/
 */

THREE.Ruler3D = function (scene, camera, domElement, sizes) {

    var font, child, meshContain;

    var contain = new THREE.Object3D();
    var fontContain = new THREE.Object3D();
    var childHelp = new THREE.Object3D();

    var mouse = new THREE.Vector2();
    var rotateStart = new THREE.Vector2();
    var rotateEnd = new THREE.Vector2();
    var rotateDelta = new THREE.Vector2();
    var fontRotate;
    var array = [];

    var ruler = this;

    this.scene = scene;
    this.camera = camera;
    this.domElement = ( domElement !== undefined ) ? domElement : document;
    this.sizes = ( sizes !== undefined ) ? sizes : 1000;
    this.step = this.sizes / 10;

    this.initRuler = function () {
        //z gird

        var _geometry = new THREE.Geometry();

        var _material = new THREE.LineBasicMaterial({
            color: 0xdad5cb,
            opacity: 0.8
        });
        for (var i = -ruler.sizes; _i <= ruler.sizes; _i += ruler.step) {
            _geometry.vertices.push(new THREE.Vector3(-ruler.sizes, _i, 0));
            _geometry.vertices.push(new THREE.Vector3(ruler.sizes, _i, 0));
            _geometry.vertices.push(new THREE.Vector3(_i, -ruler.sizes, 0));
            _geometry.vertices.push(new THREE.Vector3(_i, ruler.sizes, 0));
        }
        var _linez = new THREE.LineSegments(_geometry, _material);
        contain.add(_linez);

        //x gird

        var _geometry = new THREE.Geometry();

        for (var _i = -ruler.sizes; _i <= ruler.sizes; _i += ruler.step) {
            _geometry.vertices.push(new THREE.Vector3(0, _i, -ruler.sizes));
            _geometry.vertices.push(new THREE.Vector3(0, _i, ruler.sizes));
            _geometry.vertices.push(new THREE.Vector3(0, -ruler.sizes, _i));
            _geometry.vertices.push(new THREE.Vector3(0, ruler.sizes, _i));
        }
        var _material = new THREE.LineBasicMaterial({
            color: 0xdad5cb,
            opacity: 0.8
        });
        var _linex = new THREE.LineSegments(_geometry, _material);
        contain.add(_linex);

        //y gird

        var _geometry = new THREE.Geometry();

        for (var _i = -ruler.sizes; _i <= ruler.sizes; _i += ruler.step) {
            _geometry.vertices.push(new THREE.Vector3(_i, 0, -ruler.sizes));
            _geometry.vertices.push(new THREE.Vector3(_i, 0, ruler.sizes));
            _geometry.vertices.push(new THREE.Vector3(-ruler.sizes, 0, _i));
            _geometry.vertices.push(new THREE.Vector3(ruler.sizes, 0, _i));
        }
        var _material = new THREE.LineBasicMaterial({
            color: 0xdad5cb,
            opacity: 0.8
        });
        var _liney = new THREE.LineSegments(_geometry, _material);
        contain.add(_liney);

        //

        var _geometry = new THREE.Geometry();

        _geometry.vertices.push(new THREE.Vector3(-ruler.sizes, 0, 0));
        _geometry.vertices.push(new THREE.Vector3(ruler.sizes, 0, 0));


        var _material = new THREE.LineBasicMaterial({
            color: 0xff0000,
            opacity: 0.8
        });

        var _lineX = new THREE.LineSegments(_geometry, _material);
        contain.add(_lineX);

        //

        var _geometry = new THREE.Geometry();

        _geometry.vertices.push(new THREE.Vector3(0, -ruler.sizes, 0));
        _geometry.vertices.push(new THREE.Vector3(0, ruler.sizes, 0));


        var _material = new THREE.LineBasicMaterial({
            color: 0x00ff00,
            opacity: 0.8
        });

        //

        var _lineY = new THREE.LineSegments(_geometry, _material);
        contain.add(_lineY);

        var _geometry = new THREE.Geometry();
        _geometry.vertices.push(new THREE.Vector3(0, 0, -ruler.sizes));
        _geometry.vertices.push(new THREE.Vector3(0, 0, ruler.sizes));


        var _material = new THREE.LineBasicMaterial({
            color: 0x0000ff,
            opacity: 0.8
        });

        var _lineZ = new THREE.LineSegments(_geometry, _material);
        contain.add(_lineZ);


        // axis mark

        var _axisMark = [];

        for (var _i = -ruler.sizes; _i < ruler.sizes; _i += ruler.step) {
            _axisMark.push("" + _i + "");
        }


        var _textMat = new THREE.MultiMaterial([
            new THREE.MeshPhongMaterial({color: 0x000, shading: THREE.FlatShading}), // front
            new THREE.MeshPhongMaterial({color: 0x000, shading: THREE.SmoothShading}) // side
        ]);

        var _loader = new THREE.FontLoader();
        _loader.load('js/optimer_bold.typeface.json', function (response) {

            font = response;

            for (var _i = 0; _i < _axisMark.length; _i++) {


                var _textGeo = new THREE.TextGeometry(_axisMark[_i], {

                    font: font,
                    size: ruler.sizes / 100,
                    height: 1,
                    curveSegments: 1

                });


                var _textMesh = new THREE.Mesh(_textGeo, _textMat);
                _textMesh.position.set(_axisMark[_i], 0, 0);

                contain.add(_textMesh);
                fontContain.add(_textMesh);

                var _textMesh = new THREE.Mesh(_textGeo, _textMat);
                _textMesh.position.set(0, _axisMark[_i], 0);

                contain.add(_textMesh);
                fontContain.add(_textMesh);

                var textMesh = new THREE.Mesh(_textGeo, _textMat);
                textMesh.position.set(0, 0, _axisMark[_i]);
                contain.add(textMesh);
                fontContain.add(textMesh);

            }

            for (var _i = 0; _i < ruler.scene.children.length; _i++) {

                var _child = ruler.scene.children[_i];


                if (_child.type == "Mesh") {

                    array.push(_child);


                }
            }

        });
    }
    this.selectObject = function () {


        ruler.scene.remove(meshContain);

        meshContain = new THREE.Object3D();

        childHelp = child;

        child.geometry.computeBoundingBox();

        var _px = (child.geometry.boundingBox.max.x - child.geometry.boundingBox.min.x) / 2;
        var _py = (child.geometry.boundingBox.max.y - child.geometry.boundingBox.min.y) / 2;
        var _pz = (child.geometry.boundingBox.max.z - child.geometry.boundingBox.min.z) / 2;

        var _fixPosition = new THREE.Vector3(child.position.x.toFixed(3), child.position.y.toFixed(3), child.position.z.toFixed(3));

        var _strPosition = JSON.stringify(_fixPosition);

        var _textMat = new THREE.MultiMaterial([
            new THREE.MeshPhongMaterial({
                color: Math.random() * 0xffffff,
                opacity: 0.5,
                shading: THREE.FlatShading
            }), // front
            new THREE.MeshPhongMaterial({
                color: Math.random() * 0xffffff,
                opacity: 0.5,
                shading: THREE.SmoothShading
            }) // side
        ]);

        var _textGeo = new THREE.TextGeometry("" + _strPosition + "", {

            font: font,
            size: ruler.sizes / 100,
            height: 1,
            curveSegments: 1

        });


        var _textMesh = new THREE.Mesh(_textGeo, _textMat);


        _textMesh.position.set(child.position.x + _px, child.position.y + _py, child.position.z + _pz);
        _textMesh.rotation.y = fontContain.children[0].rotation.y;

        meshContain.add(_textMesh);

        var _geometry = new THREE.BoxGeometry(Math.abs(child.position.x), Math.abs(child.position.y), 2);
        var _material = new THREE.MeshBasicMaterial({color: 0xff0000, opacity: 0.2, transparent: true});
        var _plane = new THREE.Mesh(_geometry, _material);
        _plane.position.set(child.position.x / 2, child.position.y / 2, child.position.z);
        meshContain.add(_plane);


        var _geometry = new THREE.BoxGeometry(Math.abs(child.position.x), Math.abs(child.position.z), 2);
        var _material = new THREE.MeshBasicMaterial({color: 0x00ff00, opacity: 0.2, transparent: true});
        var _plane = new THREE.Mesh(_geometry, _material);
        _plane.position.set(child.position.x / 2, child.position.y, child.position.z / 2);
        _plane.rotation.x = Math.PI / 2;
        meshContain.add(_plane);

        var _geometry = new THREE.BoxGeometry(Math.abs(child.position.z), Math.abs(child.position.y), 2);
        var _material = new THREE.MeshBasicMaterial({color: 0x0000ff, opacity: 0.2, transparent: true});
        var _plane = new THREE.Mesh(_geometry, _material);
        _plane.position.set(child.position.x, child.position.y / 2, child.position.z / 2);
        _plane.rotation.y = Math.PI / 2;
        meshContain.add(_plane);

        ruler.scene.add(meshContain);
    }

    this.mouseMove = function (event) {

        event.preventDefault();

        mouse.x = ( event.clientX / window.innerWidth ) * 2 - 1;
        mouse.y = -( event.clientY / window.innerHeight ) * 2 + 1;

        var _raycaster = new THREE.Raycaster();

        _raycaster.setFromCamera(mouse, ruler.camera);

        var _intersects = _raycaster.intersectObjects(array);


        if (_intersects.length > 0) {

            if (child != _intersects[0].object) {

                child = _intersects[0].object;

                if (child != childHelp) {

                    ruler.selectObject();
                }
            }

        }
    }
    this.mouseDown = function (event) {
        ruler.domElement.addEventListener('mousemove', ruler.onMouseMove, false);
    }
    this.onMouseMove = function (event) {


        var _element = ruler.domElement === document ? ruler.domElement.body : ruler.domElement;


        rotateEnd.set(event.clientX, event.clientY);
        rotateDelta.subVectors(rotateEnd, rotateStart);

        var _temp = 2 * Math.PI * rotateDelta.x / _element.clientWidth;

        if (_temp < 0.06 && _temp > -0.06) {

            for (var _i = 0; _i < fontContain.children.length; _i++) {

                fontContain.children[_i].rotation.y -= 2 * Math.PI * rotateDelta.x / _element.clientWidth;
            }
        }


        rotateStart.copy(rotateEnd);

    }
    this.mouseUp = function (event) {
        ruler.domElement.removeEventListener('mousemove', ruler.onMouseMove, false);
    }

    this.scene.add(contain);
    this.scene.add(fontContain);

    this.initRuler();

    ruler.domElement.addEventListener('mousemove', this.mouseMove, false);

    ruler.domElement.addEventListener('mousedown', this.mouseDown, false);

    ruler.domElement.addEventListener('mouseup', this.mouseUp, false);

}
