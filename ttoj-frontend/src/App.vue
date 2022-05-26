<template>
  <div id="app" style="height: 100%;">
      <router-view v-if="isRouterAlive" style="height: 100%;"/>
  </div>
</template>

<script>
import types from "./store/types";
import {recursionRouter} from "./router/recursion-router";
import router from "./router";
import {dynamicRouter} from "./router/dynamic-router"
import manageRouter from "./router/dynamic-router"
import ChatBox from "./components/ChatBox";
import {deepClone} from "./utils/util"

export default {
  name: 'App',
  provide () {    //父组件中通过provide来提供变量，在子组件中通过inject来注入变量。
    return {
      reload: this.reload
    }
  },
  mounted() {
    let w = window.innerWidth || document.documentElement.clientWidth  || document.body.clientWidth;
    console.log(w);
    // document.body.setAttribute('min-width', '950px')
  },
  created() {
    this.$store.dispatch(types.Get_LoginUser).then(res => {
      console.log('Get_LoginUser: ');
      console.log(res);
      if (!res.success){

      }else{
        let loginUser = res.data.loginUser
        if (!!loginUser){
          this.$store.commit(types.Set_LoginUser, res.data.loginUser)
          let routes = recursionRouter(loginUser.manageRouters, manageRouter);
          for (let i in routes){
            router.addRoute('dynamicRoutesParent', routes[i]);
          }
        }
      }
    }).catch(err => {
      err = JSON.stringify(err)
      this.errorNotify(err)
    })
  },
  data(){
    return{
      isRouterAlive: true
    }
  },
  methods: {
    reload () {
      this.isRouterAlive = false;            //先关闭，
      this.$nextTick(function () {
        this.isRouterAlive = true;         //再打开
      })
    }
  },

}
</script>

<style lang="less">

</style>
