function calc(){
var money = $('#benjin').val()-0;// 本金
var days = $('#days').val()-0;// 投资期限
var lilv = $('#lilv1').val()-0;// 收益率
var coupon = $('#coupon').val()-0;// 优惠券金额(元)
var lilv2 = (money * lilv / 365 * days + coupon) / days * 365 / money;
lilv2 = (lilv2 * 100).toFixed(2);
$('#lilv2').val(lilv2 + "%");
}
$(document).ready(function(){
$("#benjin").html(localStorage['logs'])
});
});

