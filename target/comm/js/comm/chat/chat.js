/**
 * Created by IntelliJ IDEA.
 * User: ASUS
 * Date: 2020/4/23
 * Time: 12:14
 */

var websocket = null;
// 局域网内用户聊天需要换成局域网ip
var ipAddress = "localhost";
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://"+ipAddress+":8080/Communi_war/websocket/server");
} else if ('MozWebSocket' in window) {
    websocket = new MozWebSocket("ws://"+ipAddress+":8080/Communi_war/websocket/server");
} else {
    websocket = new SockJS("http://"+ipAddress+":8080/Communi_war/sockjs/server");
}

//连接发生错误的回调方法
websocket.onerror = function () {
    //setMessageInnerHTML("WebSocket连接发生错误");
};

//连接成功建立的回调方法
websocket.onopen = function () {
    // setMessageInnerHTML("WebSocket连接成功");
};

//接收到消息的回调方法
websocket.onmessage = function (event) {
    setMessageInnerHTML(event.data.toString());
};

//连接关闭的回调方法
websocket.onclose = function () {
    //setMessageInnerHTML("WebSocket连接关闭");
};

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    closeWebSocket();
};

//关闭WebSocket连接
function closeWebSocket() {
    websocket.close();
}

/**
 * emoji表情添加函数
 */
function addEmoji(emoji) {
    var textMsg = $("#textMsg");
    textMsg.append(emoji.innerHTML);
}
/**
 * emoji弹出框函数
 */
$(function popover(){
    $("[data-toggle='popover']").popover({html : true });
});

/**
 * 弹出框的 x 图标的函数，调用emoji点击一次关闭弹出框
 */
function popover(){
    $("#emoji").click();
}

/**
 * 调用 <input> click()函数装载图片
 */
function imageUp() {
    $("#imageLoader").click();
    $("#textMsg").append("<img id=\"image\" hidden alt=\"image\" src=\"\" style=\"max-width: 700px;\">");
}

/**
 * 加载图片到输入框中显示
 */
function loadImage() {
    $("#image").attr("src", URL.createObjectURL($("#imageLoader")[0].files[0])).attr("hidden", false);
    $("#textMsg").focus();
}

/**
 * 添加当前用户的一个消息
 */
function addUserMessage(messageBox, html) {
    //获取用户头像url
    var userIconPath = $("#userIcon").attr("src");
    messageBox.append(
        "<div class=\"col-xs-12\">\n" +
        "<div style=\"float: right;\">\n" +
        "<span class=\"messageR\">"+html+"</span><span class=\"MsgRTail\"></span>" +
        "<img src=\""+ userIconPath +"\" class=\"img-circle messageIcon\" alt=\"icon\"/>" +
        "</div>\n" +
        "</div>");
}

/**
 * 添加当前用户聊天对象的一个消息
 */
function addChatUserMessage(messageBox, html) {
    //获取聊天对象头像路径值
    var chatUserIconPath = $("#chatUserIcon").val();
    messageBox.append(
        "<div class=\"col-xs-12\">\n" +
        "<img src=\""+ chatUserIconPath +"\" class=\"img-circle messageIcon\" alt=\"icon\"/>" +
        "<span class=\"MsgLTail\"></span><span class=\"messageL\">"+html+"</span>" +
        "</div>");
}

/**
 * 将接收到消息显示在messageBox或添加未读消息。
 */
function setMessageInnerHTML(msg) {
    var message = JSON.parse(msg);
    //获取当前用户id
    var chatUserId = $("#chatUserId").val();
    //获取消息容器
    var messageBox = $("#messageBox");
    //获取消息时间
    var messageTime = new Date(message.time);
    var sessionTime = messageTime.getMonth()+1+"-"+messageTime.getDate()+" "+
        messageTime.getHours()+":"+messageTime.getMinutes()+":"+messageTime.getSeconds();
    //判断是否是当前聊天对象发送的消息
    if(message.userId === chatUserId){
        //显示的消息
        if(message.type ==="text"){
            addChatUserMessage(messageBox, message.text);
        }else {
            addChatUserMessage(messageBox,"<img class='messageImg' alt=\"image\" style='width: 100%;' src=\""+message.text+"\">");
        }
        //设置图片放大显示函数
        $(".messageImg").bootstrapViewer();
        //更新会话时间
        $("#sessionTime" + message.userId).text(sessionTime);
        //更新消息状态
        $.post("updateMessageFlag",
            {userId:message.userId,chatUserId:message.chatUserId},
            function(){});
    }else{
        //添加未读消息数，或增加会话
        var unreadSpan = $("#unreadNum"+message.userId);
        if(unreadSpan.length > 0){
            //未读消息数加1
            unreadSpan.text(Number(unreadSpan.text())+1);
            //更新会话时间
            $("#sessionTime" + message.userId).text(sessionTime);
        }else {
            $.post("queryOneUser",
                {userId:message.userId},
                function(user){
                    //增加显示会话
                    $("#recentSession").after("<a class=\"list-group-item session\" onclick=\"startSession("+user.id+")\">\n" +
                        "<img id=\"chatUserIcon"+user.id+"\" src=\""+user.iconPath+"\" class=\"img-circle sessionIcon\" alt=\"icon\"/>\n" +
                        "<span id=\"unreadNum"+user.id+"\" class=\"badge\" style=\"background-color: #FF0000;\">1</span>\n" +
                        "<b id=\"chatUserName"+user.id+"\">"+user.userName+"</b><br>\n" +
                        "<div id=\"sessionTime"+user.id+"\" class=\"sessionTime\">\n" +
                        sessionTime +
                        "</div>\n" +
                        "</a>");
                }
            );
        }
    }
    var scrollHeight = messageBox.prop("scrollHeight");
    messageBox.animate({scrollTop:scrollHeight}, 500);
}

/**
 * 发送消息，显示发送的消息
 */
function send() {
    //获取消息框对象
    var textMsg = $("#textMsg");
    //获取消息内容
    var text = textMsg.text();
    //获取发送方用户Id
    var userId = $("#userId").val();
    //获取图片
    var imageLoader = $('#imageLoader');
    var image = imageLoader[0].files[0];
    //获取接收送方用户Id
    var chatUserId = $("#chatUserId").val();
    //获取当前时间
    var messageTime = new Date();
    //消息类型
    var messageType = "";
    //判断是否添加了图片
    if(typeof image != "undefined"){
        //上传图片
        var formData = new FormData();
        formData.append("file", image);
        formData.append("userId", userId);
        formData.append("time", messageTime.getTime().toString());
        $.ajax({
            //需要等待到图片上传成功才能发送消息，不然图片无法正常显示，所有取消异步传输图片。
            async: false,
            url: "upLoadImage",
            type: "POST",
            cache: false,
            data: formData,
            processData: false,
            contentType: false
        }).done(function(res) {}
        ).fail(function(res) {});
        messageType = "image";
        var imageType = image.name.split(".")[1];
        text = "../images/chat/MsgEx/"+userId+"/"+messageTime.getTime()+"."+imageType;
        //清除 imageLoader
        imageLoader.remove();
        //设置 imageLoader 目的是为了清除选中文件
        $("#emojiTrigger").after(" <input id=\"imageLoader\" type=\"file\" style=\"display: none;\" onchange=\"loadImage()\" />");
    }else {
        messageType = "text";
    }

    //生成消息的json对象
    var msgJson = { "userId":userId,
        "chatUserId":chatUserId,
        "text":text,
        "type":messageType,
        "flag":false,
        "time":messageTime};
    var msg = JSON.stringify(msgJson);
    //文本框置空
    textMsg.text('').focus();

    if(text.trim() ===""){
        alert("消息为空!");
    }else{
        //发送消息
        websocket.send(msg);

        //生成会话时间
        var sessionTime = messageTime.getMonth()+1+"-"+messageTime.getDate()+" "+
            messageTime.getHours()+":"+messageTime.getMinutes()+":"+messageTime.getSeconds();
        //更新会话时间
        $("#sessionTime"+chatUserId).text(sessionTime);

        var messageBox = $("#messageBox");
        //显示自己发送的消息
        if(messageType === "text"){
            addUserMessage(messageBox, text);
        }else {
            addUserMessage(messageBox, "<img class='messageImg' alt=\"image\" style='width: 100%;' src=\""+URL.createObjectURL(imageLoader[0].files[0])+"\">")
        }
        //设置图片放大显示函数
        $(".messageImg").bootstrapViewer();
        //显示消息的 messageBox 滑动到底部
        var scrollHeight = messageBox.prop("scrollHeight");
        messageBox.animate({scrollTop:scrollHeight}, 500);
    }
}

/**
 * 添加按键抬起事件监听
 * 在抬起回车键和消息输入框有焦点时调用 send 函数
 */
$(document).keyup(function(event){
    if(event.keyCode === 13 && $("#textMsg").is(":focus")){
        send();
    }
});

/**
 * 打开一个会话，设置会话属性
 * 查询，显示未读消息
 */
function startSession(userId){
    //设置id
    $("#chatUserId").val(userId);
    //设置头像路径值
    var chatUserIconPath = $("#chatUserIcon"+userId).attr("src");
    $("#chatUserIcon").val(chatUserIconPath);
    //设置聊天用户名
    $("#chatUserName").text("["+$("#chatUserName"+userId).text()+"]");
    //清空消息框
    var messageBox = $("#messageBox");
    messageBox.html("");
    //请求未读消息
    $.post("openSession",
        {id:userId},
        //装入未读消息
        function(messages){
            for(var index in messages) {
                //显示未读消息
                if(messages[index].type ==="text"){
                    addChatUserMessage(messageBox, messages[index].text);
                }else {
                    addChatUserMessage(messageBox,"<img class='messageImg' alt=\"image\" style='width: 100%;' src=\""+messages[index].text+"\">");
                }
                var scrollHeight = messageBox.prop("scrollHeight");
                messageBox.animate({scrollTop:scrollHeight}, 500);
            }
            //设置图片放大显示函数
            $(".messageImg").bootstrapViewer();
        }
    );
    //没有输入框添加输入框和发送键
    if($("#textMsg").length === 0){
        $("#messageInput").after("<div id=\"textMsg\"  class=\"col-xs-11 msgInput\" contenteditable=\"true\">\n" +
            "                        </div>\n" +
            "                        <button class=\"btn bg-success col-md-1 sendButton\" onclick=\"send()\">发送</button>");
    }
    //放置查询历史聊天记录的控件
    messageBox.prepend("<div class=\"text-center\">" +
        "<a id=\"viewMore\" onclick=\"queryMsgEx()\">\n" +
        "<span class=\"glyphicon glyphicon-time\"></span>\n" +
        "查看更多消息\n" +
        "</a>" +
        "</div>");
    //清空未读标志
    $("#unreadNum"+userId).text("");
}

/**
 * 查询，显示历史聊天记录
 */
function queryMsgEx(){
    var messageBox = $("#messageBox");
    var chatUserId = $("#chatUserId").val();
    //清空消息框
    messageBox.html("");
    //post请求历史聊天记录
    $.post("queryMsgEx",
        {id:chatUserId},
        //装入历史消息
        function(messages){
            for(var index in messages) {
                var userId = messages[index].userId;
                //根据id添加消息
                if(userId.toString() === chatUserId){
                    //显示当前用户聊天对象的消息
                    if(messages[index].type ==="text"){
                        addChatUserMessage(messageBox, messages[index].text);
                    }else {
                        addChatUserMessage(messageBox,"<img class='messageImg' alt=\"image\" style='width: 100%;' src=\""+messages[index].text+"\">");
                    }
                }else {
                    //显示当前用户的消息
                    if(messages[index].type ==="text"){
                        addUserMessage(messageBox, messages[index].text);
                    }else {
                        addUserMessage(messageBox,"<img class='messageImg' alt=\"image\" style='width: 100%;' src=\""+messages[index].text+"\">");
                    }
                }
            }
            //设置图片放大显示函数
            $(".messageImg").bootstrapViewer();
        }
    );
}