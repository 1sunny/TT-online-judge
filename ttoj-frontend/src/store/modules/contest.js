import {
  setTagNavListInLocalstorage,
  getTagNavListFromLocalstorage,
  getNextRoute,
  routeHasExist,
  routeEqual,
  getRouteTitleHandled,
} from '@/libs/util'

import router from '@/router'
import config from '../../config'

const closePage = (state, route) => {
  const nextRoute = getNextRoute(state.tagNavList, route)
  state.tagNavList = state.tagNavList.filter(item => {
    return !routeEqual(item, route)
  })
  console.log(nextRoute);
  router.push(!!nextRoute ? nextRoute : {name: 'contestProblems'})
}

export default {
  state: {
    tagNavList: [],
    homeRoute: {},
  },
  getters: {

  },
  mutations: {
    // list 空就从 storage 里取, 不为空就将 list 存入 storage
    setTagNavList (state, payload) {
      let {list, contestId} = payload
      console.log(list, contestId);
      let tagList = []
      if (list) {
        tagList = [...list]
      } else tagList = getTagNavListFromLocalstorage(contestId) || []
      state.tagNavList = tagList
      setTagNavListInLocalstorage([...tagList], contestId)
    },
    // 关闭指定路由对应的标签页
    closeTag (state, route) {
      let tag = state.tagNavList.filter(item => routeEqual(item, route))
      route = tag[0] ? tag[0] : null
      if (!route) return
      closePage(state, route)
    },
    addTag (state, payload) {
      let { route, contestId} = payload
      if (config.commonTagName.indexOf(route.name) === -1){
        let router = getRouteTitleHandled(route)
        if (!routeHasExist(state.tagNavList, router)) {
          state.tagNavList.push(router)
          setTagNavListInLocalstorage([...state.tagNavList], contestId)
        }
      }
    },
  },
  actions: {

  }
}
