    $.ajax({
        type :  "POST",
        contentType: "application/json; charset=UTF-8",
        url : "https://pan.baidu.com/mbox/msg/transfer?bdstoken=6d30583a50d01666122ed84721517d77&channel=chunlei&web=1&app_id=250528&logid=MTU3NzM2NDYwMDg4ODAuOTYyMzEwMDE4MjA2ODI5Mg==&clienttype=0",
        data : {
            from_uk: "228435709",
            msg_id: "471981340980807638",
            path: "/apps/Cloud Sync/zhuanlan-all/test",
            ondup: "overwrite",
            async: "1",
            type: "2",
            gid: "658103785633267975",
            fs_ids: "[180093740597068]"
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