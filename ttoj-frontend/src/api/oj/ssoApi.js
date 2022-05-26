import request from '@/utils/request'

export function loginSubmit(loginForm){
    return request.post('/api/oj/sso/login', loginForm);
}

export function registerSubmit(registerForm){
    return request.post('/api/oj/sso/register', registerForm);
}

export function logout(){
    return request.post('/api/oj/sso/logout');
}

export default {
    loginSubmit,
    registerSubmit,
    logout
}
