<template>
  <div style="height: 300px;
              width: 500px;
              display: flex;
              margin: auto;
              background: #f5f6f7;
              flex-direction: column;
              box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
             ">
    <div style="
                width: 100%;
                border-bottom: 1px solid #e0e0e0;
                text-align: center;line-height: 2
               "
    >
      {{ chatWith }}
    </div>
    <div v-for="question in msgList"
         style="
                margin: 8px 8px;
                border-radius: 5px;
               "
    >
      <div style="display: flex;">
        <el-avatar :size="25" :src="circleUrl"/>
        <div style="margin-left: 8px;"
        >
          <div >{{ question.username }}</div>
          <!--
              span的display类型是inline。
              padding对于inline不生效，需要改变span的display类型为inline-block。
          -->
          <div class="leftMsg" style="position: relative;">
            <div  style="
                         padding: 6px 6px;
                         background: #fff;
                         border-radius: 3px;
                         display: inline-block;
                        "
            >
              {{ question.msg }}
            </div>
          </div>
        </div>
      </div>


      <div v-for="reply in question.reply"
           style="display: flex;
                  justify-content: flex-end;
                  margin-top: 8px;
                 "
      >
        <div style="margin-right: 8px;"
        >
          <div style="text-align: right">{{ reply.username }}</div>
          <div class="rightMsg" style="position: relative;">
            <div  style="
                         padding: 6px 6px;
                         background: #409EFF;
                         border-radius: 3px;
                         display: inline-block;
                         color: #fff;
                        "
            >
              {{ reply.msg }}
            </div>
          </div>
        </div>
        <el-avatar :size="25" :src="circleUrl"/>
      </div>
    </div>
  </div>
</template>

<script>

let socket;

export default {
  name: "ChatBox",
  props: {
    msgList: {
      type: Array,
      default: () => [
        {
          msg: '你好,我有个问题',
          username: '111',
          reply: [
            {
              msg: '请说',
              username: 'admin'
            },
            {
              msg: '你说不说?',
              username: 'admin'
            }
          ]
        },
        {
          msg: '我也有个问题',
          username: '222'
        }
      ]
    },
    chatWith: {
      type: String,
      default: '111'
    }
  },
  data() {
    return {
      circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
      user: {},
      isCollapse: false,
      users: [],
      chatUser: '',
      text: "",
      messages: [],
      content: ''
    }
  },
  created() {
    // this.init()
  },
  methods: {
    send() {
      if (!this.chatUser) {
        this.warnNotify("请选择聊天对象")
        return;
      }
      if (!this.text) {
        this.warnNotify("请输入内容")
      } else {
        if (typeof (WebSocket) == "undefined") {
          console.log("您的浏览器不支持WebSocket");
        } else {
          console.log("您的浏览器支持WebSocket");
          // 组装待发送的消息 json
          // {"from": "zhang", "to": "admin", "text": "聊天文本"}
          let message = {from: this.user.username, to: this.chatUser, text: this.text}
          socket.send(JSON.stringify(message));  // 将组装好的json发送给服务端，由服务端进行转发
          this.messages.push({user: this.user.username, text: this.text})
          // 构建消息内容，本人消息
          this.createContent(null, this.user.username, this.text)
          this.text = '';
        }
      }
    },
    createContent(remoteUser, nowUser, text) {  // 这个方法是用来将 json的聊天消息数据转换成 html的。
      let html
      // 当前用户消息
      if (nowUser) { // nowUser 表示是否显示当前用户发送的聊天消息，绿色气泡
        html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
            "  <div class=\"el-col el-col-22\" style=\"text-align: right; padding-right: 10px\">\n" +
            "    <div class=\"tip left\">" + text + "</div>\n" +
            "  </div>\n" +
            "  <div class=\"el-col el-col-2\">\n" +
            "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
            "    <img src=\"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\" style=\"object-fit: cover;\">\n" +
            "  </span>\n" +
            "  </div>\n" +
            "</div>";
      } else if (remoteUser) {   // remoteUser表示远程用户聊天消息，蓝色的气泡
        html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
            "  <div class=\"el-col el-col-2\" style=\"text-align: right\">\n" +
            "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
            "    <img src=\"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\" style=\"object-fit: cover;\">\n" +
            "  </span>\n" +
            "  </div>\n" +
            "  <div class=\"el-col el-col-22\" style=\"text-align: left; padding-left: 10px\">\n" +
            "    <div class=\"tip right\">" + text + "</div>\n" +
            "  </div>\n" +
            "</div>";
      }
      console.log(html)
      this.content += html;
    },
    init() {
      this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
      let username = this.user.username;
      let _this = this;
      if (typeof (WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
      } else {
        console.log("您的浏览器支持WebSocket");
        let socketUrl = "ws://localhost:9090/imserver/" + username;
        if (socket != null) {
          socket.close();
          socket = null;
        }
        // 开启一个websocket服务
        socket = new WebSocket(socketUrl);
        //打开事件
        socket.onopen = function () {
          console.log("websocket已打开");
        };
        //  浏览器端收消息，获得从服务端发送过来的文本消息
        socket.onmessage = function (msg) {
          console.log("收到数据====" + msg.data)
          let data = JSON.parse(msg.data)  // 对收到的json数据进行解析， 类似这样的： {"users": [{"username": "zhang"},{ "username": "admin"}]}
          if (data.users) {  // 获取在线人员信息
            _this.users = data.users.filter(user => user.username !== username)  // 获取当前连接的所有用户信息，并且排除自身，自己不会出现在自己的聊天列表里
          } else {
            // 如果服务器端发送过来的json数据 不包含 users 这个key，那么发送过来的就是聊天文本json数据
            //  // {"from": "zhang", "text": "hello"}
            if (data.from === _this.chatUser) {
              _this.messages.push(data)
              // 构建消息内容
              _this.createContent(data.from, null, data.text)
            }
          }
        };
        //关闭事件
        socket.onclose = function () {
          console.log("websocket已关闭");
        };
        //发生了错误事件
        socket.onerror = function () {
          console.log("websocket发生了错误");
        }
      }
    }

  }
}

</script>

<style>
.leftMsg:before{
  content: '';
  width: 0;
  height: 0;
  border-right: 10px solid #fff;
  border-bottom: 6px solid transparent;
  position: absolute;
  top: 0;
  left: -8px;
}
.rightMsg:after{
  content: '';
  width: 0;
  height: 0;
  border-left: 10px solid #409EFF;
  border-bottom: 6px solid transparent;
  position: absolute;
  top: 0;
  right: -8px;
}
</style>
