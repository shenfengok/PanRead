const puppeteer = require("puppeteer");

class core {
    constructor() {
        this.page = {};
        // this.login();
        this.token = '';
        this.replace = [];
    }

     async http_call(url,datas,isget,strict) {
        return  await this.page.evaluate((url,datas,isget,strict) =>{
             const p = new Promise((resolve, reject) => {
                 $.ajax(url, {
                     type : isget ? "GET" : "POST",
                     contentType: "application/json;charset=UTF-8",
                     url : url,
                     timeout: 40000,
                     data : datas,
                     success: function (result) {
                         console.log(result);
                         if(strict){
                             if(result.errno !== 0){
                                 var at=1;
                             }
                             resolve(result.errno);
                         }else{
                             resolve(result);
                         }

                     },
                     error: function (e ) {
                         resolve(e);
                         console.log(e);
                         if(strict){
                             resolve(500);
                         }
                         // reject(new Error())
                     }
                 })
             })
             return p
         }, url,datas,isget,strict);
    }
    async  fetch_share_list(fsid){
        var list_data = {};
        var resultList = [];
        var page = 1;
        while(page < 10){
            this.sleep(200)
            var result = await this.http_call(this.get_share_list_url(fsid,page),list_data,true);
            resultList = resultList.concat(result.records);
            if(result.has_more){

                page++;
            }else{
                break;
            }
        }
        return resultList;
    }
     get_share_list_url(fsid,page){
        return "https://pan.baidu.com/mbox/msg/shareinfo?msg_id=471981340980807638&page="+page+"&from_uk=228435709&gid=658103785633267975&type=2&fs_id="+fsid+"&num=300&bdstoken="+this.token+"&channel=chunlei&web=1&app_id=250528&clienttype=0";
    }
    async sleep (duration)  {
        return new Promise((resolve, reject) => {
            setTimeout(resolve, duration);
        });
    };


    async login(){
        const browser = await puppeteer.launch({
            ignoreHTTPSErrors: true,ignoreDefaultArgs: ["--enable-automation"], headless: false,args: [ '--no-sandbox', '--disable-setuid-sandbox' ],
        });

        this.page = await browser.newPage();
        await this.page.setViewport({width: 1920, height: 1080});
        await this.page.setUserAgent('Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36');

        const ps= await browser.pages();
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
        const cookieString ="BIDUPSID=A88BBACB2CB8E5A639D0852CE3653923; PSTM=1575081216; BAIDUID=A88BBACB2CB8E5A6688729D50C9A7C23:FG=1; BDUSS=ZpR2MwV28zWlBHaWl4dXF-WXREdFVLaDFQR3cyN29kTlRaWjk5SmwyWmpNUTllSVFBQUFBJCQAAAAAAAAAAAEAAABr357z3bHD0sLSysAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGOk511jpOddaD; pan_login_way=1; PANWEB=1; t_pcnt=20191226-0; STOKEN=1680db029adc3d6f18f017e2243ca08a0f72315c12fbde8523ad19f97d0f41aa; SCRC=4795e6e5deb3f39b81060070fa233bcf; BDCLND=tdZw4cHCRGQfCpIEAD%2Fesydxx6LH%2BQRhCCOwUCe6Cto%3D; cflag=13%3A3; BDORZ=FFFB88E999055A3F8A630C64834BD6D0; Hm_lvt_7a3960b6f067eb0085b7f96ff5e660b0=1579354649,1579434921,1579435607,1579436026; Hm_lpvt_7a3960b6f067eb0085b7f96ff5e660b0=1579436028; PANPSC=1310663448266826621%3ADJI9ZdfpjgKV2doCoUnx9Ey7txDAjOFZAvuw0%2Bn1dPHZlPIhre87bdvIifvnJmpQ1HTbxvuL7YeaV1QHy3lx2%2FMqglGyp%2Bmcc9zt75Hl8Msu%2FGN14ZZ7XoNFii8DW%2FOStGFmCHaQZPljy4bwAz5jQiHq5mg%2FcPBDsGdcW9T0tiRCZq5V4NOJbTfR87iwHFNUMKjxtxPUcwo%3D";
        await addCookies(cookieString, this.page, 'pan.baidu.com');
        //云盘的文件夹路径
        await this.page.goto("https://pan.baidu.com/mbox/homepage#share/type=session", {
            timeout: 600000,
            waitUntil: "networkidle2"
        });

        // console.log(token);
        await this.page.waitForSelector('.list-content-box');
        let sel ='#test';
        this.token = await this.page.evaluate((sel) => {
            return yunData.MYBDSTOKEN;
        }, sel);
    }
    async toCrawFolder(fid){

        //let url =this.get_share_list_url("892227217147466",1);
        // this.list = await  this.fetch_share_list(fid);
        //todo,加入过滤路径
        var list = await this.fetch_share_list(fid);
        for(let i=0;i < list.length;i ++){
            let foo = list[i];
            this.replace.push(foo.path);
        }
        console.log(list);
    }
    async toCrawItem(fid){
        //todo 加入replace 字符串
    }
}


module.exports = core;