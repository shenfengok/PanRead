
function getSign(sign1,sign2,sign3) {
    var signFnc;
    try {
        signFnc = new Function("return " + sign2)();
    } catch (e) {
        throw new Error(e.message);
    }
    return base64Encode(signFnc(sign3, sign1));
}

function base64Encode(t) {
    var a, r, e, n, i, s, o = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    for (e = t.length, r = 0, a = ""; e > r;) {
        if (n = 255 & t.charCodeAt(r++), r == e) {
            a += o.charAt(n >> 2);
            a += o.charAt((3 & n) << 4);
            a += "==";
            break;
        }
        if (i = t.charCodeAt(r++), r == e) {
            a += o.charAt(n >> 2);
            a += o.charAt((3 & n) << 4 | (240 & i) >> 4);
            a += o.charAt((15 & i) << 2);
            a += "=";
            break;
        }
        s = t.charCodeAt(r++);
        a += o.charAt(n >> 2);
        a += o.charAt((3 & n) << 4 | (240 & i) >> 4);
        a += o.charAt((15 & i) << 2 | (192 & s) >> 6);
        a += o.charAt(63 & s);
    }
    return a;
}
function getLogID(cookieStr) {
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

    return w(getCookie(cookieStr,name));
}

function getCookie(cookieStr,e) {
    var o, t;
    var n = cookieStr, c = decodeURI;
    return n.length > 0 && (o = n.indexOf(e + "="), -1 != o) ? (o = o + e.length + 1, t = n.indexOf(";", o), -1 == t && (t = n.length), c(n.substring(o, t))) : "";
}
