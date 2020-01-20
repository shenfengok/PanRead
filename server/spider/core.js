const puppeteer = require("puppeteer");

class core {

    async login(){
        const browser = await puppeteer.launch({
            ignoreHTTPSErrors: true,ignoreDefaultArgs: ["--enable-automation"], headless: false,args: [ '--no-sandbox', '--disable-setuid-sandbox' ],
        });

        const page = await browser.newPage();
        await page.setViewport({width: 1920, height: 1080});
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
        await addCookies(cookieString, page, 'pan.baidu.com');
        //云盘的文件夹路径
        await page.goto("https://pan.baidu.com/mbox/homepage#share/type=session", {
            timeout: 600000,
            waitUntil: "networkidle2"
        });
        let sel ='#test';
        const token = await page.evaluate((sel) => {
            return yunData.MYBDSTOKEN;
        }, sel);
        console.log(token);
        await page.waitForSelector('.list-content-box');
        return page;
    }
}


module.exports = core;