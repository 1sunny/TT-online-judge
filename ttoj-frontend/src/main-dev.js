import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'

Vue.config.productionTip = false

/*
全局样式
*/
import './css/style.css'

/*
element ui
 */
import ELEMENT from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'element-ui/lib/theme-chalk/display.css';
Vue.use(ELEMENT)　　//注意大写

/*
iView
 */
import iview from "view-design";
import 'view-design/dist/styles/iview.css';
Vue.use(iview)

/*
dayjs
 */
import dayjs from 'dayjs';
import duration from 'dayjs/plugin/duration'
dayjs.extend(duration)
Vue.prototype.$dayjs = dayjs;

/*
markdown 编辑器
 */
import MavonEditor from 'mavon-editor' // components
import 'mavon-editor/dist/css/index.css' // styles
Vue.use(MavonEditor)

/*
moment
 */
// import moment from 'moment'
// Vue.prototype.$moment = moment
// moment.locale('zh-cn')

/*
codemirror
 */
import VueCodemirror from 'vue-codemirror'
//  base style
import 'codemirror/lib/codemirror.css'
Vue.use(VueCodemirror, /* {
  options: { theme: 'base16-dark', ... },
  events: ['scroll', ...]
} */)

/*
组织树结构
 */
import OrgTree from 'v-org-tree'
import 'v-org-tree/dist/v-org-tree.css'
Vue.use(OrgTree)

/*
注册指令
 */
import importDirective from "./directive";
importDirective(Vue)
import { directive as clickOutside } from 'v-click-outside-x'
Vue.directive('clickOutside', clickOutside)

import config from "@/config"
Vue.prototype.$config = config

Vue.prototype.errorNotify = (message) =>{
  ELEMENT.Notification.error({
    title: '失败',
    message,
    duration: 0
  });
}

Vue.prototype.successNotify = (message) =>{
  ELEMENT.Notification.success({
    title: '成功',
    message,
    duration: 1000
  });
}

Vue.prototype.warnNotify = (message) =>{
  ELEMENT.Notification.warning({
    title: '警告',
    message,
    duration: 1000
  });
}

Vue.prototype.infoNotify = (message) =>{
  ELEMENT.Notification.info({
    title: '消息',
    message,
    duration: 1000
  });
}

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
