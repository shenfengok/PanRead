// ==UserScript==
// @name         pan read
// @namespace    shenfeng
// @version      1.0.0
// @author       shenfeng
// @match        *://pan.baidu.com/disk/home*
// @match        *://yun.baidu.com/disk/home*
// @match        *://pan.baidu.com/s/*
// @match        *://yun.baidu.com/s/*
// @match        *://pan.baidu.com/share/link*
// @match        *://yun.baidu.com/share/link*
// @match 			 *://pan.baidu.com/*
// @require      https://cdn.staticfile.org/jquery/3.5.0/jquery.min.js
// @grant        GM.xmlHttpRequest
// @grant       GM.getValue
// @grant       GM.setValue
// @noframes
// ==/UserScript==


const parentKey = 'parent_key';

function putGlobal(key, value) {
    GM.setValue(key, value);
}

unsafeWindow.PARENT_LIST = [];


(async () => {
    const response = await get('http://localhost:8080/api/test')
    console.log(response)


    //事件监听和标记
    do_job_steps(function () {
        $('.sharelist-item-title a').unbind('click').bind('click', async function () {
            var item = $(this);
            let fid = item.parents('li').attr('data-fid');
            let tile = item.attr('title');
            //标记到his导航栏a上
            do_job_steps(function () {
                let his = $('.sharelist-history a:contains("' + tile + '")');
                if (his.length > 0) {
                    his.attr('fid-hack', fid);

                    return true;
                }
                return false;
            });

            //标记到span标签上
            do_job_steps(function () {
                let his2 = $('li[node-type=sharelist-history-list] span[title="' + tile + '"]');
                if (his2.length > 0) {
                    his2.attr('fid-hack', fid);

                    return true;
                }
                return false;
            });


        })
        return false;
    })

    //获取类型，展示操作
    do_job_steps(function () {
        let list = $('.sharelist-item-title a');//判断类型是否赋值
        if (list.length > 0) {
            let parent_id = get_parent_id();

            for (let l in list) {
                if (l.attr('marked-type')) {
                    continue
                } else {
                    //获取类型
                    //getParentIfNo()
                    //getSelf();
                }
            }

            return true;
        }
        return false;
    });

})()


function get_parent_id() {
    return $('li[node-type=sharelist-history-list] span').last().attr('fid-hack')
}


//----帮助类helper------

//helper funcs
function do_job_steps(...steps) {
    var job = []
    for (var i = 0; i < steps.length; i++) {
        job.push(steps[i])
    }
    do_job(job)
}

function do_job(job) {
    var step_count = job.length;
    var current_step = 0;
    var t1 = window.setInterval(function () {
        if (job[current_step]()) {
            current_step++;
            step_count--;
        }
        if (step_count == 0) {
            window.clearInterval(t1);
        }
    }, 2000);
}


function get(erUrl) {
    return new Promise((resolve, reject) => {
        GM.xmlHttpRequest({
            method: 'GET',
            url: erUrl,
            headers: {'Content-type': 'application/json'},
            onload: function (xhr) {
                console.log(xhr.responseText);
                res = JSON.parse(xhr.responseText);
                return resolve(res);
            },
            onerror: function (xhr) {
                console.log("Error fetching JSON from xhr.");
                return reject(false);
            }
        });
    })
}

function post(erUrl, param) {
    return new Promise((resolve, reject) => {
        GM.xmlHttpRequest({
            method: 'POST',
            url: erUrl,
            data: para,
            headers: {'Content-type': 'application/json'},
            onload: function (xhr) {
                console.log(xhr.responseText);
                res = JSON.parse(xhr.responseText);
                return resolve(res);
            },
            onerror: function (xhr) {
                console.log("Error fetching JSON from xhr.");
                return reject(false);
            }
        });
    })
}
