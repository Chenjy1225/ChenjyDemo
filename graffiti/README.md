3D-Graffiti 功能面板简介。

> 仅供学习和交流，版权归原作者所有


## pencil

### pencil 

* `pencil` ：画笔功能，开启后可以在`sketchpad_target`面板上涂鸦。`default：false`

> `pencil`画笔功能、`eraser`橡皮擦功能和`camera_control`相机控件不能同时开启。若要开启其他功能则需先关闭当前功能。

### pencil_size

* `pencil_size` ：控制画笔的粗细程度。`default：5` `max：20` `min:0.8`

`tips` 本页面画笔尺寸小于0.8以后涂鸦产生线段会比较模糊。


### pencil_color

* `pencil_color` ：控制画笔的颜色。`default：#44abda`

## eraser

### eraser

* `eraser` ：橡皮擦功能。`default：false`

`tips` 该功能不可以连续的进行参数。

### eraser_type

* `eraser_type` ：橡皮擦样式，提供的橡皮擦样式有限。`cube` `sphere`供选择。`default：cube`

### eraser_size

* `eraser_size` ：橡皮擦的大小。`default：20` `max：100` `min:5`

## sketchpad

### sketchpad_target 

* `sketchpad_target` ：要绘制的面,分为`x-y`、`x-z`、`y-z`。`default：x-y`

### sketchpad_distance

* `sketchpad_x_y` ：`x-y`离`z`轴中心点的距离。`default：0` `max：-500` `min:500`
* `sketchpad_y_z` ：`x-z`离`y`轴中心点的距离。`default：0` `max：-500` `min:500`
* `sketchpad_x_z` ：`y-z`离`x`轴中心点的距离。`default：0` `max：-500` `min:500`

## camera

### camera_control

* `camera_control` ：相机控件功能，开启后可以通过鼠标控制场景移动。`default：false`

> `camera_control`相机控件功能开启后不能通过面板对参数进行配置。此处有`bug` 需要将次功能关闭后可配置。

### camera_position
	
* `camera_position` ：控制相机的视角。`default：x-y`

## BasicMesh

### BasicCube

* `BasicCube` ：向场景中添加一个`cube`。

### BasicSphere

* `BasicSphere` ：向场景中添加一个`sphere`。

## exportOBJ

* `exportOBJ` ：导出模型功能，将涂鸦完成的场景以OBJ格式下载到本地。

## importOBJ

* `importOBJ` ：导入模型功能，将本地模型OBJ文件导入到场景。

## exportSTL

* `exportSTL` ：导出模型功能，将涂鸦完成的场景以STL格式下载到本地。

## clearScene

* `clearScene` ：清空场景功能。

## clearLastLine

* `clearLastLine` ：清除绘制的上一条线。
