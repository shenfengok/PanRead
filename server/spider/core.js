const puppeteer = require("puppeteer");

class core {
    constructor() {
        this.page = {};
        this.login();
        this.token = '';
        this.replace = [];

    }

    async http_call(url, datas, isget, strict) {
        return this.page.evaluate((url, datas, isget, strict) => {
            const p = new Promise((resolve, reject) => {
                $.ajax(url, {
                    type: isget ? "GET" : "POST",
                    contentType: "application/json;charset=UTF-8",
                    url: url,
                    timeout: 40000,
                    data: datas,
                    success: function (result) {
                        console.log(result);
                        if (strict) {
                            if (result.errno !== 0) {
                                var at = 1;
                            }
                            resolve(result.errno);
                        } else {
                            resolve(result);
                        }

                    },
                    error: function (e) {
                        resolve(e);
                        console.log(e);
                        if (strict) {
                            resolve(500);
                        }
                        // reject(new Error())
                    }
                })
            })
            return p
        }, url, datas, isget, strict);
    }

    //获取文件夹下所有内容
    async get_folder_child_content(fsid) {
        var list_data = {};
        var resultList = [];
        var page = 1;
        while (page < 10) {
            this.sleep(200)
            var result = await this.http_call(this.get_folder_child_content_url(fsid, page), list_data, true);
            resultList = resultList.concat(result.records);
            if (result.has_more) {

                page++;
            } else {
                break;
            }
        }
        return resultList;
    }

    get_folder_child_content_url(fsid, page) {
        return "https://pan.baidu.com/mbox/msg/shareinfo?msg_id=471981340980807638&page=" + page + "&from_uk=228435709&gid=658103785633267975&type=2&fs_id=" + fsid + "&num=300&bdstoken=" + this.token + "&channel=chunlei&web=1&app_id=250528&clienttype=0";
    }

    async sleep(duration) {
        return new Promise((resolve, reject) => {
            setTimeout(resolve, duration);
        });
    };


    async login() {
        const browser = await puppeteer.launch({
            ignoreHTTPSErrors: true,
            ignoreDefaultArgs: ["--enable-automation"],
            headless: false,
            args: ['--no-sandbox', '--disable-setuid-sandbox'],
        });

        this.page = await browser.newPage();
        await this.page.setViewport({width: 1920, height: 1080});
        await this.page.setUserAgent('Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36');

        const ps = await browser.pages();
        await ps[0].close();
        const addCookies = async (cookies_str, page, domain) => {
            let cookies = cookies_str.split(';').map(
                pair => {
                    let name = pair.trim().slice(0, pair.trim().indexOf('='));
                    let value = pair.trim().slice(pair.trim().indexOf('=') + 1);
                    return {name, value, domain}
                });
            await Promise.all(cookies.map(pair => {
                return page.setCookie(pair)
            }))
        };
        const cookieString = "BAIDUID=3A5606DA0B031D4A3247F8A6CF3CA139:FG=1; BDUSS=FvTmU3SGlhYWxBMERwQWVuUTg2MjNGS1puekFXR2pQfnNpQm9ZWFM4Q2Rsb0ZmRVFBQUFBJCQAAAAAAAAAAAEAAABr357z3bHD0sLSysAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJ0JWl-dCVpfT1; BDUSS_BFESS=FvTmU3SGlhYWxBMERwQWVuUTg2MjNGS1puekFXR2pQfnNpQm9ZWFM4Q2Rsb0ZmRVFBQUFBJCQAAAAAAAAAAAEAAABr357z3bHD0sLSysAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJ0JWl-dCVpfT1; pan_login_way=1; PANWEB=1; STOKEN=db6335f42b3a3f3e20bca1b8eaadab3ddfdb218d851d8d42dcd4624b08c01a3b; SCRC=f22e02210ee7e614de2b427a4a7ecab3; BIDUPSID=3A5606DA0B031D4A3247F8A6CF3CA139; PSTM=1599737263; BDCLND=H7yKQYFhm4GLQ3ay8sXnNL42GSOlYNMECW8C%2FihLVLw%3D; recommendTime=android2020-09-11%2020%3A26%3A00%20; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; Hm_lvt_7a3960b6f067eb0085b7f96ff5e660b0=1599736223,1599807582,1600072456,1600149840; Hm_lpvt_7a3960b6f067eb0085b7f96ff5e660b0=1600149844; PANPSC=17726692248997980610%3ADJI9ZdfpjgKV2doCoUnx9Ey7txDAjOFZAvuw0%2Bn1dPHZlPIhre87bdvIifvnJmpQ1HTbxvuL7YeaV1QHy3lx2ysL9B6RlQ7rI1%2BbtH%2BxEkIu%2FGN14ZZ7XoMUFw37y0KsoHpXTuaU%2F4ljy4bwAz5jQiHq5mg%2FcPBDsGdcW9T0tiRm65hzsZIwfCLkIoG3iFZjMKjxtxPUcwo%3D";
        await addCookies(cookieString, this.page, 'pan.baidu.com');
        //云盘的文件夹路径
        await this.page.goto("https://pan.baidu.com/mbox/homepage#share/type=session", {
            timeout: 600000,
            waitUntil: "networkidle2"
        });

        // console.log(token);
        await this.page.waitForSelector('.list-content-box');
        let sel = '#test';
        this.token = await this.page.evaluate((sel) => {
            return yunData.MYBDSTOKEN;
        }, sel);
    }

    async crawContent(list) {
        let big_obj = {};
        for (let i in list) {
            let content_list = await this.get_folder_child_content(list[i]);
            await this.convert_course(content_list, big_obj, true);
            break;//测试
        }
        return big_obj;
    }

    async convert_course(list, obj, finish) {
        finish = false;
        for (var s = 0; s < list.length; s++) {
            var sc = list[s];
            console.log(sc.server_filename);
            obj[sc.server_filename] = {
                finish: finish,
            };
            obj[sc.server_filename].fsid = sc.fs_id;
            await this.convert_folder_content(sc.fs_id, obj[sc.server_filename]);
        }
    }

    async convert_folder_content(fsid, obj) {

        await this.sleep(200);
        let list = await this.get_folder_child_content(fsid);

        for (let s = 0; s < list.length; s++) {
            let sc = list[s];

            //文件没必要
            if (sc.isdir === 1) {
                await this.convert_folder_content(sc.fs_id, obj);
            } else {
                if (this.is_skip(sc.server_filename)) {
                    continue;
                }
                obj[sc.server_filename] = {};
                let cobj = obj[sc.server_filename];
                cobj.fsid = sc.fs_id;
                cobj.size = sc.size;
                cobj.path = this.get_path(sc.path);
                // cobj.dlink = await this.get_dlink(sc.fs_id);
                // console.log(cobj.dlink);
            }
        }
    }

    async get_dlink(fid) {
        let url = "https://pan.baidu.com/api/sharedownload?sign=&timestamp=&bdstoken=" + this.token
            + "&channel=chunlei&web=1&app_id=250528&logid=MTU3NzM3MDg4NzY5NTAuOTE4MDE3MjE1OTY0NDEy&clienttype=0";
        let data = {
            encrypt: "0",
            uk: "3757516097",
            product: "mbox",
            primaryid: "471981340980807638",
            fid_list: "[" + fid + "]",
            extra: "{\"type\":\"group\",\"gid\":\"658103785633267975\"}"
        };
        let result = {};
        while (true) {
            this.sleep(1000);
            console.log("trying")
            result = await this.http_call(url, data, true);
            if (result.list && result.list.length > 0 && result.list[0].dlink) {
                break;
            }
        }

        return result.list[0].dlink;
    }

    is_skip(file_name) {
        if (file_name.endsWith(".pdf")) {
            return true;
        }
        return false;
    }

    get_path(name) {
        let str = name;
        for (let i = 0; i < this.replace.length; i++) {
            str = str.replace(this.replace[i], "");
        }
        return str;
    }

    //获取所有子文件夹
    async toCrawFolder(fid) {
        let fidList = [];
        let list = await this.get_folder_child_content(fid);
        for (let i = 0; i < list.length; i++) {
            let foo = list[i];
            this.replace.push(foo.path);
            fidList.push(foo.fs_id.toString());
        }
        return fidList;
    }

    //获取当前目录
    async toCrawItem(fid) {
        var list = await this.get_folder_child_content(fid);
        for (let i = 0; i < list.length; i++) {
            let foo = list[i];
            let rep = foo.path.replace('/' + foo.server_filename, '')
            this.replace.push(rep);
            break;
        }
    }
}


module.exports = core;