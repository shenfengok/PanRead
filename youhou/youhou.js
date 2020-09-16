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
const QUERY_CHILD_TYPE = '/api/node/queryChildType';
const MARK_NODE_TYPE = '/api/node/markNodeType';
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

    //获取父类型，展示操纵
    do_job_steps(checkParentType);
    // 获取子Node类型，展示操作
    do_job_steps(checkChildType);

    //button点击操作
    do_job_steps(listenButtonClick);

})()


//-------dom操作--------
/**
 * 父node对应的span标签
 * @type {string}
 */
const parent_span = 'li[node-type=sharelist-history-list] span:last';
const hack_id = 'fid-hack';
const hack_name = 'name-hack';
/**
 * 所有的item对应的标签
 * @type {string}
 */
const BUTTON_GROUP = 'div.sharelist-container  div.sharelist-item-title > span';
const BUTTON_GROUP_BUTTONS = BUTTON_GROUP + '> button';
const BUTTON_GROP_RESET_CONTENT = ' <span class="button"><em class="icon"></em><span class="name">文件库</span></span>';

const BUTTON_FILLED_For = 'buttonFilledFor';

/**
 * node type为none时填充的button
 * @type {string}
 */
const BUTTON_WHEN_NODE_TYPE_NONE = '<space>&nbsp;</space><button class="mark">lib</button> ';

/**
 * node type为lib或者书籍的时候填充
 * @type {string}
 */
const BUTTON_WHEN_NODE_TYPE_LIB_OR_SERIES = '<space>&nbsp;</space><button>sync</button><space>&nbsp;</space><button class="mark">none</button>';

function get_parent_id() {
    return do_for_element(parent_span, x => x.attr(hack_id));
}

function get_parent_name() {
    return do_for_element(parent_span, x => x.attr(hack_name));
}


//-------业务类dom---------
/**
 * 扩展标签显示（none，lib ，lib out，etc);
 * @param str
 * @param tag
 * @returns {boolean}
 */
function append_parent_span(str) {

    return do_for_element(parent_span, x => {
        x.prepend(str);
        x.attr(NODE_TYPE, 1)
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
        fill_button_by_type(type, x)
    });

}

function fill_button_by_type(type, x) {
    x.children('button').remove();
    x.children('space').remove();
    x.children('font').remove();
    x.append('<font>(' + type + ')</font>');
    if ('none' === type) {
        x.append(BUTTON_WHEN_NODE_TYPE_NONE);
    } else if ('lib' === type || 'series' === type) {
        x.append(BUTTON_WHEN_NODE_TYPE_LIB_OR_SERIES);
    }
    x.attr(BUTTON_FILLED_For, type);

}


/**
 * 监听node点击
 * @returns {Promise<boolean>}
 */
async function listenNodeClick() {
    $('.sharelist-item-title a').unbind('click').bind('click', async function () {
        let item = $(this);
        let fid = get_fsid_for_item(item);
        let tile = item.attr('title');
        //标记到his导航栏a上
        do_job_steps(function () {
            let his = $('.sharelist-history a:contains("' + tile + '")');
            if (his.length > 0) {
                his.attr(hack_id, fid);
                his.attr(hack_name, tile);

                return true;
            }
            return false;
        });

        //标记到span标签上
        do_job_steps(function () {
            let his2 = $('li[node-type=sharelist-history-list] span[title="' + tile + '"]');
            if (his2.length > 0) {
                his2.attr(hack_id, fid);
                his2.attr(hack_name, tile);

                return false;
            }
            return false;
        });
    })
    return false;
}

/**
 * 为item获取fsid
 * @param it
 * @returns {undefined|*|null}
 */
function get_fsid_for_item(it) {
    return it.parents('li').attr('data-fid');
}

/**
 * 为item获取title
 * @param it
 * @returns {undefined|*|null}
 */
function get_title_for_item(it) {
    return it.siblings('a').attr('title');
}

/**
 * 监听button点击
 */
async function listenButtonClick() {
    do_for_element(BUTTON_GROUP_BUTTONS, x => {
        x.unbind('click').bind('click', async function () {
            let btn = $(this);
            let type = btn.text();
            if ('lib' === type || 'none' === type) {
                let name = get_title_for_item(btn);
                let fsid = get_fsid_for_item(btn);
                await markNodeType(fsid, type, name);
                fill_button_by_type(type, btn.parent('span'));
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
    // if (!unsafeWindow.yunData.SHARE_UK) {
    //     return false;
    // }
    // console.log('refresh');
    let param = {};
    param.cookie = document.cookie;
    param.yunDataTxt = JSON.stringify(unsafeWindow.yunData);
    param.logId = getLogID();
    param.sign = getSign();
    const response = await post(REFRESH_TOKEN, param)
    // console.log(response);
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
async function markNodeType(fsid, type, name) {
    let res = await post(MARK_NODE_TYPE, {fsId: fsid, type: type, name});
    checkRes(res, '更新Node类型');
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
 * todo
 * todo step 1: 获取可以更新的列表，含层次结构
 * todo step 2: 轮询获取列表中的每一项（下载连接，文章，视频）
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

/**
 * 检查返回的response
 * @param res
 * @param title
 */
function checkRes(res, title) {
    if (res.status === 'ok') {
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

function errorMsg(msg) {
    Toast.fire({
        text: '失败：' + msg,
        icon: 'error'
    })
}

/**
 * 检查父文件夹类型
 */
async function checkParentType() {

    //跳过条件，所有的job都有跳过条件
    if (do_for_element(parent_span, x => x.attr(NODE_TYPE) === "1")) {
        return;
    }
    let parent_id = get_parent_id();
    let parent_name = get_parent_name();
    if (parent_id && parent_name) {

        let res = await post(QUERY_NODE_TYPE, {fsId: parent_id, name:parent_name });
        if (res) {
            if (res.data && res.data.nodeType) {
                let type = res.data.nodeType;
                append_parent_span('(' + type + ')');
                // fill_button(type);
            } else {
                errorMsg(res);
            }

        }
    }


    return false;
}

/**
 * 检查zi文件夹类型
 */
async function checkChildType() {

    //跳过条件
    if ($(BUTTON_GROUP).length === $(BUTTON_GROUP + '> button.mark').length) {
        return;
    }
    let parent_id = get_parent_id();
    let parent_name = get_parent_name();
    if (parent_id) {
        let res = await post(QUERY_CHILD_TYPE, {fsId: parent_id, name: parent_name});
        if (res) {
            if (res.data && res.data.length > 0) {
                let map = {};
                for (let i = 0; i < res.data.length; i++) {
                    let item = res.data[i];
                    map[item.fsid] = item.nodeType;
                }
                do_for_element(BUTTON_GROUP, x => {
                    x.each(function (index, el) {
                        let it = $(el);
                        let fsid = get_fsid_for_item(it);
                        let type = map[fsid];
                        fill_button_by_type(type, it);
                    })
                })
            } else {
                errorMsg(res.msg);
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



