<template>
  <div id="headerWrapper"
       style="position: fixed;
              top: 0;
              left: 0;
              height: auto;
              width: 100%;
              z-index: 999;
             "

  >
    <div class="hidden-xs-only"
         style="width: 100%;border-bottom: solid 1px #e6e6e6;">
      <el-menu
          :default-active="$route.path"
          class="el-menu-demo"
          mode="horizontal"
          router
          @select="handleSelect"
          ellipsis="false"
      >
        <div style="height: 45px;
                    float: left;
                    display: flex;
                    align-items: center;
                    margin: 0 20px;
                   "
        >
          <el-image
              style="height: 20px
                  "
              src="/static/TTOJ.svg"
              fit="contain">
          </el-image>
        </div>
        <el-menu-item index="/home">
          <Icon type="md-home"/>
          主页
        </el-menu-item>
        <el-menu-item index="/problems">
          <Icon type="md-list"/>
          题库
        </el-menu-item>
        <el-menu-item index="/contest">
          <Icon type="md-medal"/>
          比赛
        </el-menu-item>
        <el-menu-item index="/forum">
          <Icon type="ios-chatboxes"/>
          讨论
        </el-menu-item>
        <el-menu-item v-if="showManageBtn" index="/manage">
          <Icon type="md-construct" />
          管理
        </el-menu-item>
        <template v-if="auth">
          <el-submenu index="" style="float: right;vertical-align: center;margin-right: 10px">
            <template #title>
              <el-image
                  style="width: 20px; height: 20px;border-radius: 50%"
                  :src="$config.baseUrl+user.avatar"
                  :fit="'contain'"></el-image>
              <span style="margin-left: 5px">{{ username }}</span>
            </template>
            <el-menu-item :index="/user/+username">个人</el-menu-item>
            <el-menu-item @click="logout">退出</el-menu-item>
          </el-submenu>
        </template>
        <template v-else>
          <div style="display: flex;
                      justify-content: flex-end;
                      align-items: center;
                      height: 45px;
                     "
          >
            <el-button size="small" @click="loginDialogVisible = true">登录</el-button>
            <el-button size="small"
                       @click="registerDialogVisible = true"
                       style="margin-right: 20px"
            >注册
            </el-button>
          </div>
        </template>
      </el-menu>
    </div>
    <el-dialog
        :visible.sync="loginDialogVisible"
        title="登录"
        width="25%"
        :before-close="handleLoginClose"
        :append-to-body="true"
    >
      <el-form ref="loginForm"
               :model="loginForm"
               label-position="right"
               label-width="80px"
               :rules="loginRules"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loginSubmit">登录</el-button>
          <el-button @click="closeLoginForm">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog
        :visible.sync="registerDialogVisible"
        title="注册"
        width="25%"
        :before-close="handleRegisterClose"
        :append-to-body="true"
    >
      <el-form ref="registerForm"
               :model="registerForm"
               label-position="right"
               label-width="80px"
               :rules="registerRules"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="registerForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="registerForm.password" type="password" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="registerForm.confirmPassword" type="password" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="registerSubmit">注册</el-button>
          <el-button @click="closeRegisterForm">取消</el-button>
        </el-form-item>
      </el-form>

    </el-dialog>
    <div class="hidden-sm-and-up"
         style="height: 45px;
                width: 100%;
                padding: 0 20px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                border-bottom: solid 1px #e6e6e6;
                "
    >
      <div @click="menuDrawer = !menuDrawer">
        <Icon type="md-menu" size="18"/>
      </div>
      <el-image
          style="height: 20px;
                 flex: 1 1 auto;
                "
          src="/static/TTOJ.svg"
          fit="contain">
      </el-image>
      <div>
        <Icon type="ios-notifications" size="18"/>
      </div>
    </div>
    <el-drawer
        :visible.sync="menuDrawer"
        direction="ltr"
        size="70%"
        :withHeader="false"
    >
      <el-menu :default-active="$route.path"
                class="el-menu-demo"
                mode="vertical"
                router
                ellipsis="false"
               @select="handleSelect"
      >
        <el-menu-item index="/home">
          <Icon type="md-home"/>
          主页
        </el-menu-item>
        <el-menu-item index="/problems">
          <Icon type="md-list"/>
          题库
        </el-menu-item>
        <el-menu-item index="/contest">
          <Icon type="md-medal"/>
          比赛
        </el-menu-item>
        <el-menu-item index="/forum">
          <Icon type="ios-chatboxes"/>
          讨论
        </el-menu-item>
        <el-menu-item v-if="showManageBtn" index="/manage">管理</el-menu-item>
        <template v-if="auth">
          <el-submenu>
            <template #title>
              <el-image
                  style="width: 20px; height: 20px;border-radius: 50%"
                  :src="$config.baseUrl + user.avatar"
                  :fit="'contain'"></el-image>
              <span style="margin-left: 5px">{{ username }}</span>
            </template>
<!--            <el-menu-item :index="/user/+username">个人</el-menu-item>-->
            <el-menu-item @click="logout">退出</el-menu-item>
          </el-submenu>
        </template>
        <template v-else>
          <div>
            <el-button size="small" @click="loginDialogVisible = true">登录</el-button>
            <el-button size="small"
                       @click="registerDialogVisible = true"
                       style="margin-right: 20px"
            >注册
            </el-button>
          </div>
        </template>
      </el-menu>
    </el-drawer>
  </div>
</template>

<script>
import types from '../store/types'
import storage from "../utils/storage";
import {mapGetters} from "vuex";
import {loginSubmit, logout, registerSubmit} from "../api/oj/ssoApi";
import {recursionRouter} from "@/router/recursion-router";
import manageRouter from "@/router/dynamic-router";
import router from "@/router";

export default {
  name: "Header",
  data() {
    return {
      menuDrawer: false,
      loginDialogVisible: false,
      registerDialogVisible: false,
      loginForm: {},
      registerForm: {},
      loginRules: {
        username: [
          {
            required: true,
            message: '用户名不能为空',
            trigger: 'blur'
          },
          {
            min: 3, max: 12, message: '长度为3到12个字符'
          }
        ],
        password: [
          {
            required: true,
            message: '密码不能为空',
            trigger: 'blur'
          },
          {
            min: 3, max: 18, message: '长度为3到18个字符'
          }
        ]
      },
      registerRules: {
        username: [
          {
            required: true,
            message: '用户名不能为空',
            trigger: 'blur'
          },
          {
            min: 3, max: 12, message: '长度为3到12个字符'
          }
        ],
        password: [
          {
            required: true,
            message: '密码不能为空',
            trigger: 'blur'
          },
          {
            min: 3, max: 18, message: '长度为3到18个字符'
          }
        ],
        confirmPassword: [
          {
            required: true,
            message: '密码不能为空',
            trigger: 'blur'
          },
          {
            min: 3, max: 18, message: '长度为3到18个字符'
          }
        ]
      }
    }
  },
  computed: {
    ...mapGetters([
      'userId',
      'username',
      'auth',
      'user',
      'showManageBtn'
    ]),
  },
  created() {
    console.log(this.showManageBtn);
    console.log(this.user.avatar);
  },
  mounted() {
    // let fullScreen = this.$route.meta.fullScreen
    // console.log(fullScreen);
    // console.log(document.getElementById('headerWrapper').getAttribute('max-width'));
    // if (!!fullScreen){
    //   document.getElementById('headerWrapper').setAttribute('max-width', '100vw')
    //   console.log(document.getElementById('headerWrapper').getAttribute('max-width'));
    // }
  },
  watch:{

  },
  methods: {
    goToProfile() {
      this.$router.push('/user/' + this.username)
    },
    loginSubmit() {
      this.$refs['loginForm'].validate(valid => {
        if (valid) {
          loginSubmit(this.loginForm).then(res => {
            console.log(res)
            if (res.success) {
              this.loginDialogVisible = false
              this.successNotify(res.message)
              let token = res.data.token
              let loginUser = res.data.loginUser
              if (!!loginUser) {
                storage.set('token', token)
                this.$store.commit(types.Set_LoginUser, loginUser)
                let routes = recursionRouter(loginUser.manageRouters, manageRouter);
                for (let i in routes) {
                  router.addRoute('dynamicRoutesParent', routes[i]);
                }
              }
            } else {
              this.errorNotify(res.message)
            }
          }).catch(err => {
            console.log(err);
            err = JSON.stringify(err)
            this.errorNotify(err)
          })
        } else {
          this.warnNotify('登录参数不合法,请重试')
        }
      })
    },
    registerSubmit() {
      this.$refs['registerForm'].validate(valid => {
        if (valid) {
          registerSubmit(this.registerForm).then(res => {
            console.log(res)
            if (res.success) {
              this.registerDialogVisible = false
            } else {
              this.errorNotify(res.message)
            }
          }).catch(err => {
            err = JSON.stringify(err)
            this.errorNotify(err)
          })
        } else {
          this.warnNotify('注册参数不合法,请重试')
        }
      })
    },
    closeLoginForm() {
      this.loginDialogVisible = false
      this.loginForm = {}
    },
    closeRegisterForm() {
      this.registerDialogVisible = false
      this.registerForm = {}
    },
    handleLoginClose() {
      this.loginDialogVisible = false
    },
    handleRegisterClose() {
      this.registerDialogVisible = false
    },
    handleSelect() {
      this.menuDrawer = false
    },
    logout() {
      logout().then(res => {
        console.log(res);
        if (res.success) {
          storage.remove("token")
          this.$store.commit(types.Clear_LoginUser)
          this.$router.push('/')
          this.successNotify(res.message)
        } else {
          this.errorNotify(res.message)
        }
      }).catch(err => {
        err = JSON.stringify(err)
        this.errorNotify(err)
      })
    }
  }
}
</script>

<style scoped>

</style>
