function isNull(str) {
    return str == null || str == undefined || str == '';
}

function dateGt(a, b) {
    var arr = a.split("/");
    var starttime = new Date(arr[2], arr[0] - 1, arr[1]);
    var starttimes = starttime.getTime();

    var arrs = b.split("/");
    var endtime = new Date(arrs[2], arrs[0] - 1, arrs[1]);
    var endtimes = endtime.getTime();

    if (starttimes > endtimes) {
        return true;
    }
    else {
        return false;
    }
}

function myformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

function myparser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}

