

$.ajax({
    type :  "POST",
    contentType: "application/json; charset=UTF-8",
    url : "https://pan.baidu.com/api/sharedownload?sign=&timestamp=&bdstoken=6d30583a50d01666122ed84721517d77&channel=chunlei&web=1&app_id=250528&logid=MTU3NzM3MDg4NzY5NTAuOTE4MDE3MjE1OTY0NDEy&clienttype=0",
    data : {
       encrypt: "0",
        uk: "3757516097",
        product: "mbox",
        primaryid: "471981340980807638",
        fid_list: "[170679717728575]",
        extra: "{\"type\":\"group\",\"gid\":\"658103785633267975\"}"
    },
    success : function(rs) {
        result = rs;
        console.log(rs);
    },
    error : function(e){
        console.log(e.status);
        console.log(e.responseText);
    }
});