function showNext(e) {
    var page =e.getAttribute("data-id");
    console.log(page);
    $.ajax({
        type:"post",
            url:"/",
            data:
            "page="+page,
        success:function (response) {
            console.log(response);
            var list =(response.data);
            $(".media").detach();
            for(var i=0;i<list.length;i++) {

                var mediaLeftElement = $("<div/>", {
                    "class": "media-left"
                }).append($("<img/>", {
                    "class": "media-object img-rounded",
                    "src": list[i].user.avatarUrl
                }));

                var mediaBodyElement = $("<div/>", {
                    "class": "media-body"
                }).append($("<h4/>", {
                    "class": "media-heading",
                    "html": "<a href='/question/" + list[i].id + "'>" + list[i].title + "</a>"
                })).append($("<span/>", {
                    "class": "text-desc"
                }).append($("<span/>", {
                        "html": list[i].commentCount + "个回复 • " + list[i].viewCount + "次浏览 • " + moment(list[i].gmtCreate).format('YYYY-MM-DD HH:mm')
                    })
                ));
                var mediaElement = $("<div/>", {
                    "class": "media"
                }).append(mediaLeftElement).append(mediaBodyElement);
                $(".fenyelan").before(mediaElement);
            }


            $(".pagination").detach();

            $(".fenyelan").append($("<ul/>", {"class" :"pagination"}))

            if (response.showFirstPage){
                $(".pagination").append($("<li/>",{
                    "html" :"<a onclick='showNext(this)' data-id='1'>"+"<span aria-hidden='true'>&lt;&lt;</span>"+"</a>"
                }))
            }
            var previouspage = response.page -1;
            var nextpage = response.page +1;
            if (response.showPrevious){
                $(".pagination").append($("<li/>",{
                    "html" :"<a onclick='showNext(this)' data-id=' "+ previouspage+" '>"+"<span aria-hidden='true'>&lt;</span>"+"</a>"
                }));
            }

            for (var i=0;i<response.pages.length;i++){
                var index = response.pages[i];
                $(".pagination").append("<li id='li-"+index +"'><a onclick='showNext(this)' data-id=' "+ index +"'><span aria-hidden='true'>"+index+"</span></a></li> "
                );
               if (index==page){
                   console.log("当前页码为:"+index);
                   $("#li-" + index).attr('class','active')
               }
            }

            if (response.showNext){
                $(".pagination").append($("<li/>",{
                    "html" :"<a onclick='showNext(this)' data-id=' "+ nextpage+"'>"+"<span aria-hidden='true'>&gt;</span>"+"</a>"
                }))

            }
            var previouspage = response.page -1;
            if (response.showEndPage){
                $(".pagination").append($("<li/>",{
                    "html" :"<a onclick='showNext(this)' data-id=' "+response.totalPage+" '>"+"<span aria-hidden='true'>&gt;&gt;</span>"+"</a>"
                }))
            }




        },
        dataType:"json"
    });
}