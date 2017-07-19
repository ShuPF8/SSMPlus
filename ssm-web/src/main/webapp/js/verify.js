var map = {};
function Encrypt(FMK1){    var YWDjq2 = CryptoJS["\x65\x6e\x63"]["\x55\x74\x66\x38"]["\x70\x61\x72\x73\x65"]("\x61\x62\x63\x64\x65\x66\x67\x61\x62\x63\x64\x65\x66\x67\x31\x32");    var Eo3 = CryptoJS["\x65\x6e\x63"]["\x55\x74\x66\x38"]["\x70\x61\x72\x73\x65"](FMK1);    var PiN4 = CryptoJS["\x41\x45\x53"]["\x65\x6e\x63\x72\x79\x70\x74"](Eo3, YWDjq2, {mode:CryptoJS["\x6d\x6f\x64\x65"]["\x45\x43\x42"],padding: CryptoJS["\x70\x61\x64"]["\x50\x6b\x63\x73\x37"]});    return PiN4["\x74\x6f\x53\x74\x72\x69\x6e\x67"]();}function Decrypt(X6){    var oVBTNB7 = CryptoJS["\x65\x6e\x63"]["\x55\x74\x66\x38"]["\x70\x61\x72\x73\x65"]("\x61\x62\x63\x64\x65\x66\x67\x61\x62\x63\x64\x65\x66\x67\x31\x32");    var v8 = CryptoJS["\x41\x45\x53"]["\x64\x65\x63\x72\x79\x70\x74"](X6, oVBTNB7, {mode:CryptoJS["\x6d\x6f\x64\x65"]["\x45\x43\x42"],padding: CryptoJS["\x70\x61\x64"]["\x50\x6b\x63\x73\x37"]});    return CryptoJS["\x65\x6e\x63"]["\x55\x74\x66\x38"]["\x73\x74\x72\x69\x6e\x67\x69\x66\x79"](v8)["\x74\x6f\x53\x74\x72\x69\x6e\x67"]();}function sign(TqMGGQ10, UM11) {    var KPaWsNmrd12 = "";    for (var noyv13 = 0; noyv13 < TqMGGQ10["\x6c\x65\x6e\x67\x74\x68"]; noyv13++){        if (UM11[TqMGGQ10[noyv13]] == null || UM11[TqMGGQ10[noyv13]] == "" || UM11[TqMGGQ10[noyv13]] == undefined) {            continue;        }        KPaWsNmrd12 += TqMGGQ10[noyv13] + "\x3d" + UM11[TqMGGQ10[noyv13]]+"\x26";    }    KPaWsNmrd12 = KPaWsNmrd12["\x73\x75\x62\x73\x74\x72\x69\x6e\x67"](0, KPaWsNmrd12["\x6c\x65\x6e\x67\x74\x68"] - 1);    KPaWsNmrd12 = Encrypt(KPaWsNmrd12);    return KPaWsNmrd12;}function sortToMap(efpJgVg14) {    var _Igo15=new window["\x41\x72\x72\x61\x79"]();    for(var sQus16 in efpJgVg14) {        _Igo15["\x70\x75\x73\x68"](sQus16);    }    _Igo15["\x73\x6f\x72\x74"]();    return _Igo15;}
function dl(form) {
    var name = $("#firstname").val();
    var sex = $("#sex").val();
    var age = $("#age").val();
    map["name"] = name;
    map["age"] = age;
    map["sex"] = sex;
    map["xm-name"]="SSM";
    map["dev-name"]="spf";
    var arr2 = sortToMap(map);
    var str = sign(arr2,map);
    $.ajax({
       url:'/sign',
        type:'POST',
        data :{"name":name,"age":age,"sex":sex,"sign":str},
        dataType : 'json',
        success:function (res) {
            if (res.code == 200) {
                alert("签名成功");
            }
        }
    });
}

function checkStart(){
    check(form);
}
function check(form) {
    for (i=0;i<form.length;i++){
        form.method = "POST";
        var ele = form.elements[i];
        var msg = ele.getAttribute('message');
        if(msg && ele.value == ""){
            alert(msg + "不能为空!");
            form.elements[i].focus();
            return false;
        }
    }
    form.submit();
}