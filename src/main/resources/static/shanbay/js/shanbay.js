/**
 * Created by Herman on 2019/2/1.
 */

layui.use(['form', 'element'], function () {
    var $ = layui.jquery,
        element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
    var form = layui.form;
});

$(function () {
//加载数据网格
    loadShanbayEnglishTable();
});

var catalog = new Object();
var shanbayEnglishTable;
function loadShanbayEnglishTable() {
    layui.use('table', function () {
        shanbayEnglishTable = layui.table;
        shanbayEnglishTable.render({
            id: 'shanbayEnglishTable',
            elem: '#shanbayEnglishTable',
            url: 'queryShanbayEnglishTablePage',
            where: catalog,
            toolbar: '#shanbayEnglishToolBar',
            defaultToolbar: ['exports'],
            title: 'English',
            height: 'full-50',
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'engWord', title: '单词', width: "15%", fixed: 'left', align: 'left', sort: true},
                {field: 'translate', title: '释义', width: "85%", align: 'left'},
                {
                    hide: 'ture', title: '音频',
                    templet: function (data) {
                        return '<audio src="' + basePath + 'voice/' + data.englishVoiceUrl + '" controls="controls" id="' + data.id + '"></audio>';
                    }
                },
                {fixed: 'right', title: '操作', toolbar: '#shanbayEnglishBar', width: "7%", align: 'center'}
            ]],
            page: true,
            parseData: function (res) { //res 即为原始返回的数据
                //  handelMessage(res.data);
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.count, //解析数据长度
                    "data": res.data //解析数据列表
                };
            }
        });

        //头工具栏事件
        shanbayEnglishTable.on('toolbar(shanbayEnglishTable)', function (obj) {
            switch (obj.event) {
                case 'queryShanbayEnglishButton':
                    catalog.title = $("#catalog").val();
                    shanbayEnglishTable.reload('shanbayEnglishTable', {
                        where: catalog
                    });
                    $("#catalog").val(catalog.title);
                    break;
                case 'playAll':
                    catalog.title = $("#catalog").val();
                    shanbayEnglishTable.reload('shanbayEnglishTable', {
                        where: catalog
                    });
                    $("#catalog").val(catalog.title);
                    break;
            }
        });

        //监听行工具事件
        shanbayEnglishTable.on('tool(shanbayEnglishTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'playVoice') {
                playVoice(data.id, this);
            }
        });
    });
}

//播放/暂停
function playVoice(id, e) {
    var audio = document.getElementById(id);
    //检测播放是否已暂停.audio.paused 在播放器播放时返回false.
    if (audio.paused) {
        rePlayVoice(id, e);
    } else {
        audio.pause();// 这个就是暂停
    }
}

//循环播放
function rePlayVoice(id, e) {
    var audio = document.getElementById(id);
    audio.play();//audio.play();// 这个就是播放
    $(e).attr("class", "layui-btn layui-btn-xs layui-btn-danger");
    $(e).html("暂停");
    //监听暂停
    $('#' + id).bind('pause', function () {
        $(e).attr("class", "layui-btn layui-btn-xs");
        $(e).html("播放");
    });
    //监听结束，循环播放
    $('#' + id).bind('ended', function () {
        setTimeout(function () {
            audio.play();//audio.play();// 这个就是播放
            $(e).attr("class", "layui-btn layui-btn-xs layui-btn-danger");
            $(e).html("暂停");
        }, 500); //1s
    })
}