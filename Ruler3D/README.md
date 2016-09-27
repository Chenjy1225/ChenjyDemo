Ruler3D 功能简介。

> 仅供学习和交流，版权归原作者所有


Ruler3D提供了一个基准坐标轴，并且可以标示场景中物体坐标（目前只支持`Mesh`对象）。

### Constructor

THREE.Ruler3D(scene, camera, domElement, sizes);

API

* sizes 坐标轴大小 `default：1000`


## how to use 

Demo基于`three-raycaster.html`基础上参照*https://chenjy1225.github.io/source/three-raycaster.html* 

在此基础上
### step1 导入`js`文件和字体`typeface`文件
 
```js
		<script type="text/javascript" src="Ruler3D.js"></script>                                                                                     
                                                                                           
```                                                                                           
        
### step2 创建一个`Ruler3D`

```js

   var ruler = new THREE.Ruler3D(scene,camera,renderer.domElement,1000);

```           

