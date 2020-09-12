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
// @match 		 *://pan.baidu.com/*
// @require      https://cdn.staticfile.org/jquery/3.5.0/jquery.min.js
// @require      https://cdn.jsdelivr.net/npm/sweetalert2@9
// @grant        GM.xmlHttpRequest
// @grant       GM.getValue
// @grant       GM.setValue
// @noframes
// ==/UserScript==


const BASE_URL = 'http://localhost:8080';
const QUERY_NODE_TYPE = '/api/node/queryType';
const MARK_PARENT_TYPE = '/api/node/markParentType';
const UPDATE_PARENT = '/api/node/updateParent';
const SYNC_PARENT = '/api/node/syncParent';
const REFRESH_TOKEN = '/api/refreshToken';
const NODE_TYPE = 'nodeType';


unsafeWindow.PARENT_LIST = [];


(async () => {
    //刷新token等全局变量
    do_job_steps(refreshToken);
    //事件监听和标记
    do_job_steps(listenNodeClick);

    //获取类型，展示操作
    do_job_steps(checkParentType);

})()


//-------dom操作--------

const parent_span = 'li[node-type=sharelist-history-list] span:last';
const hack_id = 'fid-hack';
const BUTTON_GROUP = '.file-factory';
const BUTTON_GROUP_BUTTONS = BUTTON_GROUP + '> button';
const BUTTON_GROP_RESET_CONTENT = '<span class="button"><em class="icon"></em><span class="name">文件库</span></span>';

const BUTTON_FILLED_For = 'buttonFilledFor';

const BUTTON_WHEN_NODE_TYPE_NONE = '<button>lib</button><button>libOut</button>';

const BUTTON_WHEN_NODE_TYPE_LIB_OR_SERIES = '<button>update</button><button>sync</button>';

function get_parent_id() {
    return do_for_element(parent_span, x => x.attr(hack_id));
}


//-------业务类dom---------
/**
 * 扩展标签显示（none，lib ，lib out，etc);
 * @param str
 * @param tag
 * @returns {boolean}
 */
function append_parent_span(str, tag) {
    if (do_for_element(parent_span, x => x.attr(tag) === "1")) {
        return true;
    }
    return do_for_element(parent_span, x => {
        x.prepend(str);
        x.attr(tag, 1)
    });
}


/**
 * 针对sel筛选的标签，做do_func动作
 * @param sel
 * @param do_func
 * @returns {undefined|*}
 */
function do_for_element(sel, do_func) {
    let ele = $(sel);
    if (ele.length > 0) {
        return do_func(ele);
    }
    return undefined;
}

/**
 * 填充不同node类型，对应的按钮
 * @param type
 * @returns {boolean|*}
 */
function fill_button(type) {
    if (do_for_element(BUTTON_GROUP, x => x.attr(BUTTON_FILLED_For) === type)) {
        return true;
    }
    return do_for_element(BUTTON_GROUP, x => {
        x.html(BUTTON_GROP_RESET_CONTENT);
        if ('none' === type) {
            x.append(BUTTON_WHEN_NODE_TYPE_NONE);
        } else if ('lib' === type || 'series' === type) {
            x.append(BUTTON_WHEN_NODE_TYPE_LIB_OR_SERIES);
        }
        x.attr(BUTTON_FILLED_For, type);
    });

}


/**
 * 监听node点击
 * @returns {Promise<boolean>}
 */
async function listenNodeClick() {
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
}

/**
 * 监听button点击
 */
async function listenButtonClick() {
    do_for_element(BUTTON_GROUP_BUTTONS, x => {
        x.unbind('click').bind('click', async function () {
            let btn = $(this);
            let type = btn.text();
            if ('lib' === type || 'libOut' === type) {
                await markParentType(type);
            }
            if ('update' === type) {
                await updateParent();
            }
            if ('sync' === type) {
                await syncParent();
            }
        })
    });
}

/**
 * 刷新token到服务端
 * @returns {Promise<boolean>}
 */
async function refreshToken() {
    if (!unsafeWindow.yunData.SHARE_UK) {
        return false;
    }
    console.log('refresh');
    let param = {};
    param.cookie = document.cookie;
    param.yunDataTxt = JSON.stringify(unsafeWindow.yunData);
    param.logId = getLogID();
    const response = await post(REFRESH_TOKEN, param)
    console.log(response);
    return false;
}

let Toast = Swal.mixin({
    toast: true,
    position: 'top',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: false,
    onOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
})

//------远程业务处理--------
/**
 * 更新父Node 类型
 * @param type
 * @returns {Promise<void>}
 */
async function markParentType(type) {
    let parent_id = get_parent_id();

    if (parent_id) {
        let res = await post(MARK_PARENT_TYPE, {fsId: parent_id, type: type});
        checkRes(res, '更新Node类型');
    }
    return true;
}

/**
 * 更新Parent内容
 * @returns {Promise<boolean>}
 */
async function updateParent() {
    let parent_id = get_parent_id();
    if (parent_id) {
        let res = await post(UPDATE_PARENT, {fsId: parent_id});
        checkRes(res, '更新Parent内容');
    }
    return true;
}

/**
 * 同步Parent内容
 * todo 这里需要一个一个跟新，因为下载地址需要验证码
 * @returns {Promise<void>}
 */
async function syncParent() {
    let parent_id = get_parent_id();
    if (parent_id) {
        let res = await post(SYNC_PARENT, {fsId: parent_id});
        checkRes(res, '同步Parent内容');
    }
    return true;
}

function checkRes(res, title) {
    if (res.status === 'success') {
        Toast.fire({
            text: title + '成功',
            icon: 'success'
        })
    } else {
        Toast.fire({
            text: title + '失败：' + res.msg,
            icon: 'error'
        })
    }
}

function errorMsg(res) {
    Toast.fire({
        text: title + '失败：' + res.msg,
        icon: 'error'
    })
}

/**
 * 检查父文件夹类型
 */
async function checkParentType() {
    let parent_id = get_parent_id();

    if (parent_id) {

        let res = await post(QUERY_NODE_TYPE, {fsId: parent_id});
        if (res) {
            if (res.data && res.data.nodeType) {
                let type = res.data.nodeType;
                append_parent_span('(' + type + ')', NODE_TYPE);
                fill_button(type);
            } else {
                errorMsg(res);
            }

        }
    }


    return false;
}

//----帮助类helper------

//helper funcs
function do_job_steps(...steps) {
    let job = []
    for (let i = 0; i < steps.length; i++) {
        job.push(steps[i])
    }
    do_job(job)
}

function do_job(job) {
    let step_count = job.length;
    let current_step = 0;
    let t1 = window.setInterval(function () {
        let result = job[current_step]();
        if (result === true) {
            current_step++;
            step_count--;
        }
        if (step_count === 0) {
            window.clearInterval(t1);
        }
    }, 2000);
}


function get(erUrl, param) {
    return new Promise((resolve, reject) => {
        GM.xmlHttpRequest({
            method: 'GET',
            data: param,
            url: BASE_URL + erUrl,
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
            url: BASE_URL + erUrl,
            data: JSON.stringify(param),
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
