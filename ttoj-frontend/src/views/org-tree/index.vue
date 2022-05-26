<template>
    <div class="department-outer" style="position: relative">
      <div class="zoom-box">
        <zoom-controller v-model="zoom" :min="20" :max="200"></zoom-controller>
      </div>
      <div class="view-box">
        <org-view
          v-if="data"
          :data="data"
          :zoom-handled="zoomHandled"
          @on-menu-click="handleMenuClick"
        ></org-view>
      </div>
    </div>
</template>

<script>
import OrgView from './components/org-view.vue'
import ZoomController from './components/zoom-controller.vue'
import './index.less'

const menuDic = {
  edit: '编辑部门',
  detail: '查看部门',
  new: '新增子部门',
  delete: '删除部门'
}
export default {
  name: 'org_tree_page',
  components: {
    OrgView,
    ZoomController
  },
  data () {
    return {
      data: null,
      zoom: 100
    }
  },
  computed: {
    zoomHandled () {
      return this.zoom / 100
    }
  },
  methods: {
    setDepartmentData (data) {
      data.isRoot = true
      return data
    },
    handleMenuClick ({ data, key }) {
      this.$Message.success({
        duration: 5,
        content: `点击了《${data.label}》节点的'${menuDic[key]}'菜单`
      })
    },
    getDepartmentData () {
      this.data =
        {
          label: 'problem',
          children:[
            {
              label: '动态规划',
            },
            {
              label: '贪心'
            }
          ],
          id: 0
        }
      // getOrgData().then(res => {
      //   const { data } = res
      //   this.data = data
      // })
    }
  },
  mounted () {
    this.getDepartmentData()
  }
}
</script>

<style>
</style>
