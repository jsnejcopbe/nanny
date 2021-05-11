// JavaScript Document
function IsPC() {	//判断是否是PC
  var userAgentInfo = navigator.userAgent;
  var Agents = ["Android", "iPhone",
    "SymbianOS", "Windows Phone",
    "iPad", "iPod"];
  var flag = true;
  for (var v = 0; v < Agents.length; v++) {
    if (userAgentInfo.indexOf(Agents[v]) > 0) {
      flag = false;
      break;
    }
  }
  return flag;
}
var start = "mousedown";
var move = "mousemove";
var end = "mouseup";
var click = "click";
if(IsPC()){
  start = "mousedown";
  move = "mousemove";
  end = "mouseup";
}else{
  start = "touchstart";
  move = "touchmove";
  end = "touchend";
}