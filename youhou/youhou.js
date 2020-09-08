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
    await refresh();

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

async function refresh() {
    console.log('refresh');
    let param = {};
    debugger;
    param.cookie = document.cookie;
    param.yunDataTxt = JSON.stringify(unsafeWindow.yunData);
    param.logId = getLogID();
    const response = await post('http://localhost:8080/api/refreshToken', param)
    console.log(response)
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
            data: param,
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


function getLogID() {
    var name = "BAIDUID";
    var u = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/~！@#￥%……&";
    var d = /[\uD800-\uDBFF][\uDC00-\uDFFFF]|[^\x00-\x7F]/g;
    var f = String.fromCharCode;

    function l(e) {
        if (e.length < 2) {
            var n = e.charCodeAt(0);
            return 128 > n ? e : 2048 > n ? f(192 | n >>> 6) + f(128 | 63 & n) : f(224 | n >>> 12 & 15) + f(128 | n >>> 6 & 63) + f(128 | 63 & n);
        }
        var n = 65536 + 1024 * (e.charCodeAt(0) - 55296) + (e.charCodeAt(1) - 56320);
        return f(240 | n >>> 18 & 7) + f(128 | n >>> 12 & 63) + f(128 | n >>> 6 & 63) + f(128 | 63 & n);
    }

    function g(e) {
        return (e + "" + Math.random()).replace(d, l);
    }

    function m(e) {
        var n = [0, 2, 1][e.length % 3];
        var t = e.charCodeAt(0) << 16 | (e.length > 1 ? e.charCodeAt(1) : 0) << 8 | (e.length > 2 ? e.charCodeAt(2) : 0);
        var o = [u.charAt(t >>> 18), u.charAt(t >>> 12 & 63), n >= 2 ? "=" : u.charAt(t >>> 6 & 63), n >= 1 ? "=" : u.charAt(63 & t)];
        return o.join("");
    }

    function h(e) {
        return e.replace(/[\s\S]{1,3}/g, m);
    }

    function p() {
        return h(g((new Date()).getTime()));
    }

    function w(e, n) {
        return n ? p(String(e)).replace(/[+\/]/g, function (e) {
            return "+" == e ? "-" : "_";
        }).replace(/=/g, "") : p(String(e));
    }

    return w(getCookie(name));
}

function getCookie(e) {
    var o, t;
    var n = document, c = decodeURI;
    return n.cookie.length > 0 && (o = n.cookie.indexOf(e + "="), -1 != o) ? (o = o + e.length + 1, t = n.cookie.indexOf(";", o), -1 == t && (t = n.cookie.length), c(n.cookie.substring(o, t))) : "";
}