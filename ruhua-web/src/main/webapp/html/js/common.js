/*将ios android的url输入框隐藏*/
window.onload = function() {
	setTimeout(scrollTo,0,0,0);
}
var baseFn = null;
;(function(){
	String.prototype.splitStr = function(length){
		if(arguments.length > 0){
			return this.substr(0 , length) + "..." ;
		}
		return this;
	};
    String.prototype.trim=function(){
        return this.replace(/(^\s*)|(\s*$)/g, "");
    };
    String.prototype.ltrim=function(){
        return this.replace(/(^\s*)/g,"");
    };
    String.prototype.rtrim=function(){
        return this.replace(/(\s*$)/g,"");
    } ;
    Number.prototype.formatMoney = function (places, symbol, thousand, decimal) {
        places = !isNaN(places = Math.abs(places)) ? places : 2;
        symbol = symbol !== undefined ? symbol : "￥";
        thousand = thousand || ",";
        decimal = decimal || ".";
        var number = this,
            negative = number < 0 ? "-" : "",
            i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
            j = (j = i.length) > 3 ? j % 3 : 0;
        return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
    };
	/**
	 comFn 说明 	
	 	getCoorTypeName : 取坐标类型
		getCartUuid : 取游客的ID
		pageBack : 返回上一页
	 */
	function __comFn__(){
		var that = this;
        that.getHostUrl = function(){
            var __url__ = window.location.protocol + "//" ;
            __url__ += window.location.hostname ;
            __url__ += !!window.location.port ? ":"+ window.location.port : "";
            __url__ += "";
            return __url__;
        };
//        that.url = "http://ruhua.ngrok.com";
        that.url = that.getHostUrl();
		that.getCoorTypeName = function(index){
			index = arguments.length> 0 ? index : 2 ;
			var coorType = ['','phone','qq','google','baidu'];
			return coorType[index];
		};
		that.pageBack = function(){
			window.history.go(-1);
		};
        that.getArrayItems = function(arr,num){
            var temp_array = new Array();
            for (var index in arr) {
                temp_array.push(arr[index]);
            }
            var return_array = new Array();
            for (var i = 0; i<num; i++) {
                if (temp_array.length>0) {
                    var arrIndex = Math.floor(Math.random()*temp_array.length);
                    return_array[i] = temp_array[arrIndex];
                    temp_array.splice(arrIndex, 1);
                } else {
                    break;
                }
            }
            return return_array;
        }   ;
	};
	baseFn = new __comFn__();
})();

function getData(requestUrl,jsonData,callback){
    if(requestUrl==null||""==requestUrl||undefined==requestUrl){
        return ;
    }
    var postUrl=requestUrl;
    var temp;
    $.ajax({
        async: true,
        type : 'get',
        dataType : 'jsonp',
        jsonp : 'jsoncallback', //默认callback
        data : jsonData,
        timeout : 10000,
        url: baseFn.url + requestUrl + "?callback=?",  // 跨域URL
        success : function(data) {
            callback(data);
        },
        error: function(data){

        }
    });
    return temp;
}
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

function formateDate(timestamp){
    var time = new Date(timestamp);
    var y = time.getFullYear();
    var m = time.getMonth()+1;
    var d = time.getDate();
    var h = time.getHours()+1;
    var mm = time.getMinutes()+1;
    var s = time.getSeconds()+1;
    return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
}

/**
 * 微信浏览器获取openid并缓存
 */
if (isWeiXin()){
    /*var code = getQueryString("code");
    if(code != null && code.length > 0) {
        var data = {
            code: code
        };
        $.post(baseFn.url +"/webauth/getOpenIdByCode", data, function (openid) {
            sessionStorage.setItem("openid_jd_one",openid);
        });
    }*/
}
/**
 * 判断是否在微信浏览器当中
 * @returns {boolean}
 */
function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}

//自动消失信息提示框接口 String msg
var toast = function(msg, time) {//msg：提示内容，time:吐司延迟消失时间第(可以不写，不写默认1500毫秒延迟)
    $("<div class='ui-msg'><span>"
        + msg + "</span></div>").css( {
            display : "block",
            opacity : 0.80,
            position : "fixed",
            "text-align" : "center",
            background:'rgb(56, 56, 56)',
            color:'#fff',
            padding:5,
            width : "100%",
            top : 0,
            "font-family": "cursive",
            "z-index":999
        }).appendTo($("body"));
        setTimeout(function() {
            $(".ui-msg").hide();
        },time ? time : 1500);
        /*.delay(time ? time : 1500).fadeOut(400,
        function() {
            $(this).remove();
        });*/
};
/**
 * 随机数
 * @param Min
 * @param Max
 * @returns {number}
 * @constructor
 */
function GetRandomNum(Min,Max)
{
    var Range = Max - Min;
    var Rand = Math.random();
    return(Min + Math.round(Rand * Range));
}

//数组相关
Array.prototype.indexOf = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};