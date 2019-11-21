/*提交回复*/
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


function  post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,1,content);
}


/*提交二级评论*/
function  comment(e) {
    var commentId =e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content,e);
}
function comment2target(targetId,type,content,e) {
    if (!content){
        alert("回复内容不能为空哦~~~");
        return;
    }
    if (type==1){
        $.ajax({
            type:"post",
            contentType: "application/json;charset=UTF-8",
            url:"/comment",
            data:JSON.stringify({
                "parentId":targetId,
                "content":content,
                "type":type
            }),
            success:function (response) {
                if (response.code == 200)
                {
                    console.log(response);
                    $(".comments").detach();
                    $("#huifuCount").html(response.data.length)
                    for (var i =(response.data.length)-1;i>-1;i--)
                    {
                        var time  =  moment(response.data[i].gmtCreate).format('YYYY-MM-DD HH:mm')
                        $(".comment-sp").after("<div class='col-lg-12 col-md-12 col-sm-12 col-xs-12 comments'><div class='media'><div class=\"media-left\"><a href=\"#\"><img class=\"media-object img-rounded\" src='"+ response.data[i].user.avatarUrl+"'></a></div><div class='media-body'><h5 class=\"media-heading\"><span>"+ response.data[i].user.name+"</span></h5><div>"+ response.data[i].content+"</div><div class=\"menu\"><span class=\"glyphicon glyphicon-thumbs-up icon\"></span><span data-id='"+ response.data[i].id+"' onclick='collapseComments(this)' class=\"comment-icon icon\"><span class=\"glyphicon glyphicon-comment\"></span><span class='commentCount-"+ response.data[i].id+"'> "+ response.data[i].commentCount+"</span></span><span class=\"pull-right\">"+time+"</span></div><div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse sub-comments\" id='comment-"+ response.data[i].id+"'><div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\"><input type=\"text\" class=\"form-control\" placeholder=\"评论一下……\" id='input-"+response.data[i].id+"'><button type=\"button\" class=\"btn btn-success pull-right\" onclick='comment(this)' data-id='"+ response.data[i].id+"'>评论</button></div></div></div></div></div></div></div>")
                    }

                }else {
                    if (response.code ==2003){
                        var isAccpet = confirm(response.message);
                        if (isAccpet){
                            window.open("https://github.com/login/oauth/authorize?client_id=d3af778ebb90638b026e&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                            window.localStorage.setItem("closable",true);
                        }
                    }else {
                        alert(response.message);
                    }
                }
            },
            dataType:"json"
        });
    }else {
        $.ajax({
            type:"post",
            contentType: "application/json;charset=UTF-8",
            url:"/comment",
            data:JSON.stringify({
                "parentId":targetId,
                "content":content,
                "type":type
            }),
            success:function (response) {
                if (response.code == 200)
                {
                    console.log(response);
                    $("#comment-"+response.data[0].parentId).children().remove(".comments");

                    for (var i = response.data.length-1;i>-1;i--){
                        var mediaLeftElement = $("<div/>", {
                            "class": "media-left"
                        }).append($("<img/>", {
                            "class": "media-object img-rounded",
                            "src": response.data[i].user.avatarUrl
                        }));
                        var data = comment.gmtCreate;
                        var mediaBodyElement = $("<div/>", {
                            "class": "media-body"
                        }).append($("<h5/>", {
                            "class": "media-heading",
                            "html": response.data[i].user.name
                        })).append($("<div/>", {
                            "html": response.data[i].content
                        })).append("<span class='pull-right'>"+ moment(response.data[i].gmtCreate).format('YYYY-MM-DD HH:mm')+"</span>");

                        var mediaElement = $("<div/>", {
                            "class": "media"
                        }).append(mediaLeftElement).append(mediaBodyElement);

                        var commentElement = $("<div/>", {
                            "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                        }).append(mediaElement);
                        $("#comment-"+response.data[0].parentId).prepend(commentElement);

                        $(".commentCount-"+response.data[0].parentId).html(response.data.length)
                    }


                }else {
                    if (response.code ==2003){
                        var isAccpet = confirm(response.message);
                        if (isAccpet){
                            window.open("https://github.com/login/oauth/authorize?client_id=d3af778ebb90638b026e&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                            window.localStorage.setItem("closable",true);
                        }
                    }else {
                        alert(response.message);
                    }
                }
            },
            dataType:"json"
        });
    }

}
/*
* 展开二级评论
* */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);
    if (comments.hasClass("in")){
        comments.removeClass("in");
        e.classList.remove("active");
    }else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));
                    var data = comment.gmtCreate;
                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append("<span class='pull-right'>"+ moment(comment.gmtCreate).format('YYYY-MM-DD HH:mm')+"</span>");

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}



function selectTag(e) {
    var value= e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.split(",").indexOf(value) == -1){
        if (previous){
            $("#tag").val(previous+","+value);
        }else {
            $("#tag").val(value);
        }
    }
}
function showSelectTag() {
    $("#select-tag").show();
    
}
function disshowSelectTag() {
    $("#select-tag").css("display","none");
}

