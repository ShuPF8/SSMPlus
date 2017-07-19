//加密
function Encrypt(word){
    var key = CryptoJS.enc.Utf8.parse("abcdefgabcdefg12");
    var srcs = CryptoJS.enc.Utf8.parse(word);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
    return encrypted.toString();
}
//解密
function Decrypt(word){
    var key = CryptoJS.enc.Utf8.parse("abcdefgabcdefg12");
    var decrypt = CryptoJS.AES.decrypt(word, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
    return CryptoJS.enc.Utf8.stringify(decrypt).toString();
}

// 组装字符串及加密
function sign(arr2, map) {
    var str = "";
    for (var i = 0; i < arr2.length; i++){
        if (map[arr2[i]] == null || map[arr2[i]] == "" || map[arr2[i]] == undefined) {
            continue;
        }
        str += arr2[i] + "=" + map[arr2[i]]+"&";
    }
    str = str.substring(0, str.length - 1);
    str = Encrypt(str);
    return str;
}

//排序
function sortToMap(map) {
    var arr=new Array();
    for(var key in map) {
        arr.push(key);
    }
    arr.sort();
    return arr;
}