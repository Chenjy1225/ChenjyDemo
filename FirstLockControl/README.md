FirstLockControl 功能简介。

> 仅供学习和交流，版权归原作者所有


FirstLockControl是一个基于Physijs的锁屏第一人称控制器。

### Constructor

THREE.FirstLockControl(scene, camera, domElement);


API

* moveSpeed 角色移动速度 `default：200`
* lookSpeed 视角旋转速度`default：0.2`
* enabled 控制器开关`default：true`

* W  向前移动
* A  向左移动
* S  向后移动
* D  向右移动
* 鼠标 视角的旋转

## how to use 

既然是基于 Physijs的就要先导入物理引擎 参照*https://chenjy1225.github.io/2016/08/25/three-js-physics-engine/* 

在此基础上
### step1 导入控制器文件
 
```js
   
    <script type="text/javascript" src="FirstlockControl.js"></script>                                                                                      
                                                                                           
```                                                                                           
        
### step2 创建一个控制器

```js

    var controls = new THREE.FirstLockControl(scene,camera);

```           

### step3 渲染

调用controls的update方法

```js

	function render() {
	
			controls.update();
			requestAnimationFrame(render);
			renderer.render(scene, camera);
	
				}
			
```

！！！ 这里要将原有的 `scene.simulate();` 物理场景渲染移出 （因为控制器文件里面有）