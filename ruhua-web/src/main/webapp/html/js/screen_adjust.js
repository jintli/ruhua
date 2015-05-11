//屏幕方向标识，0横屏，其他值竖屏
var orientation=0;
//转屏事件，内部功能可以自定义
function screenOrientationEvent(){
  logo = document.getElementById("background-logo-img");
  if(logo != null) {
    if(orientation == 0) 
      logo.src="img/background-logo.jpg";
    else 
      logo.src="img/background-logo-pad.jpg";
  }
}
//横竖屏事件监听方法
function screenOrientationListener(){
    try{
        var iw = window.innerWidth;     
        if(iw>window.innerHeight)orientation = 90;
        else orientation = 0;
        //调用转屏事件
        screenOrientationEvent();
    } catch(e){alert(e);};
    //间隔固定事件检查是否转屏，默认500毫秒
    setTimeout("screenOrientationListener()",500);
}
//启动横竖屏事件监听
screenOrientationListener();