//在网页加载完毕后立刻执行的操作，即当HTML文档加载完毕后，立即执行某个方法
window.onload=function (){
    var vue = new Vue({
        el:"#cart_div",
        data:{
            carts:{}
        },
        methods:{
            //获取所有
            getCart:function (){
                axios({
                    method: "GET",
                    url: "cart.do",
                    params:{
                        operator:'cartInfo'
                    }
                }).then(function (value){
                    var data = value.data;
                    vue.cart = data;
                }).catch(function (reason){})
            },
            //购物车加减
            update:function (cartId,buyCount){
                axios({
                    method: "POST",
                    url: "cart.do",
                    params: {
                        operate: 'updateCart',
                        cartId: cartId,
                        buyCount: buyCount
                    }
                }).then(function (value){
                    vue.getCart()
                }).catch(function (reason){})
            },
            //删除一栏购物车
            del:function (cartId){
                axios({
                    method: "POST",
                    url: "cart.do",
                    params:{
                        operate: 'delCart',
                        cartId: cartId
                    }
                }).then(function (value){
                    vue.getCart()
                }).catch(function (reason){})
            },
            //清空购物车
            delAll:function (userId){
                axios({
                    method:"POST",
                    url:"cart.do",
                    params:{
                        operate: 'delAllCart',
                        userId: userId
                    }
                }).then(function (value){
                    vue.getCart()
                }).catch(function (reason){})
            }
        },
        mounted: function (){
            this.getCart()
        }
    })
}