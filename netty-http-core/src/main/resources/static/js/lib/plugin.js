$.extend({
    detachSelf:function () {
        $.each(arguments,function (key,val) {
            val = $(this).detach();
        });
    },
});
/**
 * 指定两个类互相转换
 * @param a 第一个类
 * @param b 第二个类
 */
$.fn.toggleTwoClass = function(a,b){
    if(this.hasClass(a)){
        this.addClass(b).removeClass(a);
    }else{
        this.addClass(a).removeClass(b);
    }
    return this;
};
