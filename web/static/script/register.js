//相当于定义一个方法，这样在页面其他位置用$("idName")就得到id为idName的控件了，方法名为$
function $(id){
    //document.getElementById(id)是获得id这个元素
    return document.getElementById(id);
}
function preRegister(){
    //用户名不能为空，而且是3-16位数字和字母组成
    var unameReg = /[0-9a-zA-Z]{3,16}/;
    //html页面中uname的id为unameText
    var unameText = $("unameText");//利用$("unameText")就得到了id=unameText的控件，利用到了function $(id)方法得到以$(id名)即可获得id名对应的控件。
    var uname = unameText.value;
    //console.log("uname"+uname);控制台输出(检查)
    //html页面中uname出错时显示的span的id为unameSpan
    var unameSpan = $("unameSpan");//利用$("unameSpan")就得到了id=unameSpan的控件，利用到了function $(id)方法得到以$(id名)即可获得id名对应的控件。
    //判断输入的内容是否符合规则
    if (!unameReg.test(uname)){
        //id=unameSpan的内容可见
        unameSpan.style.visibility="visible";
        return false;
    }else {
        //id=unameSpan的内容不可见(隐藏)
        unameSpan.style.visibility="hidden";
    }
    //密码的长度至少为8位
    var pwdTxt = $("pwdTxt");
    var pwd = pwdTxt.value ;
    var pwdReg = /[\w]{3,}/; // /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{3,}$/;
    var pwdSpan = $("pwdSpan");
    if(!pwdReg.test(pwd)){
        pwdSpan.style.visibility="visible";
        return false ;
    }else{
        pwdSpan.style.visibility="hidden";
    }
    //密码两次输入不一致
    var pwd2 = $("pwdTxt2").value ;
    var pwdSpan2 = $("pwdSpan2") ;
    if(pwd2!=pwd){
        pwdSpan2.style.visibility="visible";
        return false ;
    }else{
        pwdSpan2.style.visibility="hidden";
    }
    //请输入正确的邮箱格式
    var email = $("emailTxt").value ;
    var emailSpan = $("emailSpan");
    var emailReg = /^[a-zA-Z0-9_\.-]+@([a-zA-Z0-9-]+[\.]{1})+[a-zA-Z]+$/;
    if(!emailReg.test(email)){
        emailSpan.style.visibility="visible";
        return false ;
    }else{
        emailSpan.style.visibility="hidden";
    }
    return true ;
}

//如果想要发送异步请求，我们需要一个关键的对象XMLHttpRequest
var xmlHttpRequest;
function createXMLHttpRequest() {
    if (window.XMLHttpRequest){
        //符合DOM2标准的浏览器，xmlHttpRequest的创建方式
        xmlHttpRequest = new XMLHttpRequest();
    }else if (window.ActiveXObject){//IE浏览器
        try {
            xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
        }catch (e){
            xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
        }
    }
}
//html页面uname中失去焦点时：onblur="ckUname(this.value)"
function ckUname(uname) {
    createXMLHttpRequest();
    var url = "user.do?operate=ckUname&uname="+uname;
    xmlHttpRequest.open("GET",url,true);
    //设置回调函数
    xmlHttpRequest.onreadystatechange = ckUnameCB;
    //发送请求
    xmlHttpRequest.send();
}
function ckUnameCB() {
    if(xmlHttpRequest.readyState==4 && xmlHttpRequest.status==200){
        //xmlHttpRequest.responseText表示服务器端响应给我的文本内容
        //alert(xmlHttpRequest.responseText);
        var responseText = xmlHttpRequest.responseText;
        // {'uname':'1'}
        //alert(responseText);
        if(responseText=="{'uname':'1'}"){
            alert("用户名已经被注册！");
        }else{
            //alert("用户名可以注册！");
        }
    }
}