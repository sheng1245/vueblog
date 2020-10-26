import axios from 'axios'
import Element from 'element-ui'
import store from './store'
import router from './router'

axios.defaults.baseURL="http://localhost:8081"
//前置拦截
axios.interceptors.request.use(config => {
    
    return config;
})

//后置拦截
axios.interceptors.response.use(response=>{
    let res = response.data;

    //console.log("=======");
    //console.log(res)
    //console.log("=======");
    if(res.code == 200){

        return response;

    }else{

        //密码输错的时候的一个弹窗
        Element.Message.error("密码错误或者用户名不存在，请重新登录",{duration:3*1000});   //后面的是设置过期时间。
        //这个时候要阻止请求进入header里面。
        return Promise.reject(response.data.msg);    //提示一个错误情况。阻止冒泡。

    }
},
    error => {
        console.log(error);    //输出错误信息。
        if(error.response.data){
            error.message = error.response.data.msg;
        }
        if(error.response.status === 401){
            store.commit("REMOVE_INFO");
            router.push("/login");
        }else{
            Element.Message.error(error.message,{duration:3*1000});
            return Promise.reject(error);
        }
    })
