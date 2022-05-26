<template>
  <div style="">
    <div style="position: fixed;
                top: 46px;
                left: 0;
                height:40px;
                width: 100%;
                background:#F0F0F0;
                z-index: 998;
               "
    >
      <tags-nav :value="$route"
                @input="handleClick"
                @refresh="refresh"
                :list="[...commonTag ,...tagNavList]"
                @on-close="handleCloseTag"
                style="
                      "
      />
    </div>
    <keep-alive :include="cacheList">
      <router-view id="contestContent"
                   :key="$route.fullPath"
                   :style="{
                            marginTop: '40px',
                            overflow: 'auto',
                            maxWidth: $route.name==='contestProblem' ? '100%' : '1200px'
                           }"
      />
    </keep-alive>
  </div>
</template>

<script>
import ContestRankings from "./ContestRankings/ContestRankings";
import ContestOverview from "./ContestOverview/ContestOverview";
import ContestSubmissions from "./ContestSubmissions/ContestSubmissions";
import ContestMySubmissions from "./ContestMySubmissions/ContestMySubmissions";
import ProblemDetail from "../../Problem/ProblemDetail/ProblemDetail";
import TagsNav from "../../../components/tags-nav/tags-nav"
import {getNewTagList, routeEqual} from "../../../libs/util";
import routers from "../../../router/static-router"
import {mapMutations, mapActions, mapGetters} from 'vuex'

export default {
  inject: ['reload'],
  name: "contestDetail",
  components: {
    ContestMySubmissions,
    ContestRankings,
    ContestOverview,
    ContestSubmissions,
    ProblemDetail,
    TagsNav
  },
  data() {
    return {
      contestId: '',
      problems: [],
      direction: 'rtl',
      commonTag: [
        {
          meta: {title: "比赛说明"},
          name: "contestOverview",
          params: {}
        },
        {
          meta: {title: "题目"},
          name: "contestProblems",
          params: {}
        },
        {
          meta: {title: "排名"},
          name: "contestRankings",
          params: {}
        },
        {
          meta: {title: "我的"},
          name: "contestMy",
          params: {}
        },
        {
          meta: {title: "提交"},
          name: "contestSubmissions",
          params: {}
        }
      ],
    }
  },
  computed: {
    tagNavList() {
      return this.$store.state.contest.tagNavList
    },
    cacheList() {
      const list = ['problemDetail', ...this.commonTag.map(item => item.name), this.tagNavList.filter(item => !(item.meta && item.meta.notCache)).map(item => item.name)]
      console.log('cacheList: ' + list);
      return list
    }
  },
  created() {
    console.log('detail created');
    this.contestId = this.$route.params.contestId
    for (let tag of this.commonTag) {
      tag.params.contestId = this.contestId
    }
  },
  watch: {
    '$route'(newRoute) {
      let {name, query, params, meta} = newRoute
      if (name === 'contestProblem') {
        meta.title = this.$route.params.problemDisplayId
      }
      this.addTag({
        route: {name, params, query, meta},
        contestId: this.contestId
      })
      this.setTagNavList({
        list: getNewTagList(this.tagNavList, newRoute),
        contestId: this.contestId
      })
    }
  },
  mounted() {
    // 先获取 storage 里取打开的题目数据
    this.setTagNavList({list: undefined, contestId: this.contestId})
    const {name, params, query, meta} = this.$route
    this.addTag({
      route: {name, params, query, meta},
      contestId: this.contestId
    })
    // 如果当前打开页面不在标签栏中，跳到 overview 页
    // if (!this.tagNavList.find(item => item.name === this.$route.name)) {
    //   console.log('here');
    //   this.$router.push({
    //     name: 'contest-overview'
    //   })
    // }
  },
  methods: {
    ...mapMutations([
      'setTagNavList',
      'addTag',
      'closeTag'
    ]),
    refresh() {
      this.reload()
    },
    turnToPage(route) {
      let {name, params, query} = {}
      if (typeof route === 'string') name = route
      else {
        name = route.name
        params = route.params
        query = route.query
      }
      this.$router.push({
        name,
        params,
        query
      })
    },
    handleCloseTag(res, type, route) {
      if (type === 'refresh') {
        this.refresh()
        return
      }
      res = res.filter(item => this.$config.commonTagName.indexOf(item.name) === -1)
      if (type !== 'others') {
        if (type === 'all') {
          this.turnToPage('contestProblems')
        } else {
          if (routeEqual(this.$route, route)) {
            this.closeTag(route)
          }
        }
      }
      this.setTagNavList({list: res, contestId: this.contestId})
    },
    handleClick(item) {
      this.turnToPage(item)
    },
    /* drawer */
    handleClose(done) {
      done();
    },
  }
}
</script>

<style scoped>
@media screen and (min-width: 920px) {
  #contestContent {
    max-width: 1200px;
    margin: 0 auto;
  }
}
</style>
