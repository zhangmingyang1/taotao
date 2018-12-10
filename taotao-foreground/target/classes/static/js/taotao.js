var TT = TAOTAO = {
	checkLogin : function(){
		var _ticket = $.cookie("TOKEN");
		if(!_ticket){
            return ;
        }
        $.ajax({
			url : "http://127.0.0.1:8080/user/token/" + _ticket+"?r="+Math.random(),
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var username = data.data.username;
					var html = username + "，欢迎来到淘淘！<a href=\"http://127.0.0.1:8084/user/logout/"+_ticket+"\"+ class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
    }
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});