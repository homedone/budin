import axios from 'axios';
import qs from 'qs'
import router from '../router/index.js'

function removeLocalstorage(){
     //清除token 
    localStorage.removeItem('token');
    window.localStorage.removeItem("userInfo");
    //跳转  
    router.replace('/login');
}

export function request(url, params, method, type, header) {
    const instance = axios.create({
        baseURL: '/api',
        timeout: 100000,
        withCredentials: true,
    })

    // axios拦截器
    instance.interceptors.request.use(config => {
        if (localStorage.getItem('token')) { 
            //在请求头加入token，名字要和后端接收请求头的token名字一样 
            config.headers.Authorization=localStorage.getItem('token');  
        } 
        return config;
        
    },error => { 
        return Promise.reject(error);
     })

    instance.interceptors.response.use(response=>{
        if (response.data.code == 2030) {
            removeLocalstorage();
           } else { 
             return response; 
           } 
    },error => { 
        const { status } = error.response
        if (status === 401) { // 未授权
            removeLocalstorage();
        } 
    })
    

    if (method && method == 'post') {
        if (type && type == "params") {
            if (params) {
                // return instance.post(url, params)
                if (header == 'json') {
                    return instance.request({
                        url,
                        data: params,
                        method: 'post',
                        headers: {
                            'Content-Type': 'application/json;charset=UTF-8'
                        },
                    })
                }else if(header == 'formdata'){
                    return instance.request({
                        url,
                        data: params,
                        method: 'post',
                        headers: {
                            'Content-Type': 'multipart/form-data;charset=UTF-8'
                        },
                    })
                }else if (type == 'paramsSerializer') {
                    return instance.request({
                        url,
                        data: qs.stringify(params, { arrayFormat: 'repeat' }),
                        method: 'post',
                    })
                }else if(header=='application'){
                    return instance.request({
                        url,
                        data: params,
                        method: 'post',
                        headers: {'Content-Type':'application/x-www-form-urlencoded'}
                    })
                }else{
                    return instance.request({
                        url,
                        data: params,
                        method: 'post',
                    })
                }
            }
            else {
                return instance.post(url)
            }
        }
        else {
            // resful的形式
            if (params) {
                for (var key in params) {
                    // 拼接url
                    url = url + '/' + params[key];
                }
            }
            return instance.post(url);
        }
    } else if (!method || method == 'get') {
        if (type == 'resful' || !type) {
            // resful的形式
            if (params) {
                for (var key in params) {
                    // 拼接url
                    url = url + '/' + params[key];
                }
            }
            return instance.get(url);
        } else if (type == 'params') {
            console.log(params);
            params = {
                params: params
            }
            return instance.get(url, params)
        }
    } else if (method && method == 'put') {
        if (params) {
            return instance.put(url, params)
        } else {
            return instance.put(url)
        }
    } else if (method && method == 'delete') {
        // resful的形式
        if (params) {
            for (var key in params) {
                // 拼接url
                url = url + '/' + params[key];
            }
        }
        return instance.delete(url);
    }
}