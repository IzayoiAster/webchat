<template>
  <div id="list">

    <!-- 群聊 -->
    <ul v-if="currentList=='群聊'">
      <!-- 群聊列表 -->
      <p style="padding: 2px 4px;height: 20px">群聊列表</p>
      <li :class="{ active: currentSession?'群聊'== currentSession.username:false }"
          v-on:click="changeCurrentSession(chatObj)">
        <img class="avatar"
             src="https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1268761962,3976237305&fm=26&gp=0.jpg">
        <el-badge :is-dot="isDot[user.username+'#群聊']"><p class="name">群聊</p></el-badge>
      </li>
    </ul>

    <el-scrollbar wrap-class="userList" wrap-style="height:650px;" view-style="height:100%;" :native="false">

      <!-- 私聊 -->
      <ul v-if="currentList=='私聊'">
        <p style="padding: 2px 4px;height: 20px">用户列表</p>
        <li v-for="item in users" :class="{ active: currentSession?item.username === currentSession.username:false }"
            v-on:click="changeCurrentSession(item)"><!--   :class="[item.id === currentSession ? 'active':'']" -->
          <div style="display: flex;justify-content: space-between">
            <div>
              <el-badge :is-dot="isDot[user.username+'#'+item.username]" style="">
                <el-image class="avatar"
                          :preview-src-list="[item.userProfile]"
                          :src="item.userProfile"
                          :alt="item.nickname">
                  <div slot="error" class="image-slot">
                    <i class="el-icon-picture-outline"></i>
                  </div>
                </el-image>
              </el-badge>
              <p class="name">{{ item.nickname }}</p>
            </div>
            <div>
              <el-badge :value="item.userStateId==1?'在线':'离线'" :type="item.userStateId==1?'danger':'info'"></el-badge>
            </div>
          </div>
        </li>
      </ul>





    </el-scrollbar>

  </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
  name: 'list',
  data() {
    return {
      user: this.$store.state.currentUser,
      chatObj: {username: '群聊', nickname: '群聊'},//群聊实体对象（为方法复用而构造，对于User对象）,
      userinfo: {
        "username": "U are beautiful",
        "url": "testimg/488DD79C-CD92-4FA6-B893-19D187457C83.png"
      },
      clickFeed: null,
      replyUser: ''
    }
  },
  computed: mapState([
    //'sessions',//this.sessions映射成this.$store.state.sessions
    'users',
    'currentSession',
    'isDot',
    'currentList'
  ]),
  methods: {
    changeCurrentSession: function (currentSession) {
      this.$store.commit('changeCurrentSession', currentSession)
    },
    // moment part
    showCommentClick: function (event, feed) {
      this.clickFeed = feed;
      feed.showComt = !feed.showComt;
    },
    likeClick: function (event, feed) {
      if (feed.like === "赞") {
        if (gUserInfo) {
          feed.userLike.push(gUserInfo.username);
          feed.like = '取消';
        }
      } else {
        if (gUserInfo) {
          feed.userLike.pop();
          feed.like = '赞';
        }
      }
    },
    comtClick: function (event, feed) {
      this.clickFeed = feed;
      feed.showComt = false;
      // $(".inset").removeClass("hidden");
      // $(".inset input").focus();
      // $(".overplay").removeClass("hidden").css("opacity", "0.6");

    },
    inputClick: function (event) {
      var comText = $(".inset input").val();
      this.clickFeed.comment.push({"name": gUserInfo.username, "content": comText});
      // $(".inset").addClass("hidden");
      // $(".overplay").addClass("hidden");
      // $('.inset input').val('');
    },
    replyComt: function (event, feed, onecommet) {
      this.clickFeed = feed;
      this.replyUser = onecommet.name;
      // $(".replybox").removeClass("hidden");
      // $(".replybox input").focus();
      // $(".overplay").removeClass("hidden").css("opacity", "0.6");
    },
    replyClick: function (event) {
      var replyText = $(".replybox input").val();
      this.clickFeed.comment.push({
        "name": gUserInfo.username,
        "content": replyText,
        "reply": this.replyUser
      });
      // $(".replybox").addClass("hidden");
      // $(".overplay").addClass("hidden");
      // $(".replybox input").val('');
    }
  }
}
</script>

<style lang="scss" scoped>
#list {
  #sharebtn{
    position: relative;
    z-index:999;
    right: 100px;
    bottom: 100px;
  }
  ul {
    margin-left: 0px;
    padding-left: 0px;
    margin-left: 2px;
  }

  li {
    padding-top: 14px;
    padding-bottom: 14px;
    //padding-right: 40px;
    //border-bottom: 1px solid #292C33;
    list-style: none;
    cursor: pointer;

    &:hover {
      background-color: #D8D6D6;
    }
  }

  li.active { /*注意这个是.不是冒号:*/
    background-color: #C8C6C6;
  }

  .avatar {
    border-radius: 2px;
    width: 30px;
    height: 30px;
    vertical-align: middle;
  }

  .name {
    display: inline-block;
    margin-left: 15px;
    margin-top: 0px;
    margin-bottom: 0px;
  }

  .stateItem { //在线状态的样式
    /*position: absolute;*/
    /*left: 160px;*/
    //margin-left: 100px;
    //margin-right: 10px;
  }

  .userList {
    max-height: 600px;
  }

  .el-scrollbar__wrap.default-scrollbar__wrap {
    overflow-x: auto;
  }


  // moments part
  .overplay {
    width: 100%;
    height: 100%;
    z-index: 10;
    position: fixed;
    background: #ccc;
  }

  .container {
    margin: 100px 20px 0 20px;
  }

  .border {
    border-bottom: 1px solid #000;
    margin-bottom: 20px;
  }

  .user-bg {
    height: 400px;
    width: 100%;
    background: url(../../../public/testimg/6631482281583828547.jpg) no-repeat;
  }

  .user-self {
    position: absolute;
    top: 280px;
    right: 20px;

  }

  .like {

  }

  .like .i-like {
    color: #6495ED;
    margin-right: 5px;
    font-weight: bold;
  }

  .comment {
    border-top: 1px solid #000;
  }

  .reply {
    text-decoration: none;
    font-size: 24px;
    color: #000;
  }

  .username {
    margin-top: 60px;
  }

  .username h1 {
    font-size: 34px;
    color: #fff;
  }

  .left {
    float: left;
  }

  .user-icon {
    width: 150px;
    height: 150px;
    float: left;
    margin-left: 45px;
    padding: 5px;
    background-color: #ffffff;
    border: 1px solid #878787;
  }

  .user-icon img, .user-pic img, .user-content img {
    width: 100%;
    height: 100%;
  }

  .user-item {

  }

  .user-pic {
    width: 80px;
    height: 80px;
    float: left;
  }

  .user-content {
    width: 80%;
    margin-left: 100px;
    padding: 0 20px 20px 20px;
    position: relative;
  }

  .user-content h2 {
    font-weight: bold;
    font-size: 26px;
    color: #6495ED;
  }

  .user-content p {
    font-size: 26px;
  }

  .spacing {
    margin-bottom: 20px;
  }

  .btn {
    width: 126.5px;
    height: 50px;
    line-height: 50px;
    text-align: center;
    display: inline-block;
    margin: 10px 0;
    color: #fff;
    font-size: 26px;
    text-decoration: none;
    outline: none;

  }

  .btn span {
    margin-right: 15px;
    font-size: 30px;
  }

  .btn span:hover {
    color: cornflowerblue;
  }

  .btn-default {
    border: none;
    width: 45px;
    height: 45px;
    background: url(../../../public/testimg/sms.svg) no-repeat;
    position: absolute;
    right: 15px;
    bottom: 10px;
    outline: none;
  }

  .time {
    color: #ccc;
    font-size: 20px;
  }

  .btn-left {
    border-right: 1px solid #000;
  }

  .commit {
    width: 260px;
    height: 70px;
    padding: 2px 20px;
    background: #3d4e56;
    border-radius: 10px;
    position: absolute;
    bottom: 0px;
    right: 60px;
  }

  .com-space {
    margin: 20px;
  }

  .hidden {
    display: none;
  }

  .comments {
    margin-left: 100px;
    position: relative;
  }

  .top {
    width: 480px;
    margin: 0 0 20px 20px;
    padding: 5px 0;
    background: #F4F4F4;
  }

  .top p {
    margin: 15px;
    font-size: 24px;
    position: relative;
  }

  .triangle {
    position: absolute;
    width: 0px;
    height: 0px;
    line-height: 0px;
    border-bottom: 10px solid #F4F4F4;
    border-left: 10px solid #fff;
    border-right: 10px solid #fff;
    left: 40px;
    top: -10px;
  }

  .top .user {
    font-size: 24px;
    color: #6495ED;
  }

  .inset, .replybox {
    height: 70px;
    background: #fff;
    position: fixed;
    right: 50%;
    margin-right: -230px;
    z-index: 100;
    border: none;
    box-shadow: 2px 2px 5px #ccc inset;
  }

  .inset input, .replybox input {
    width: 300px;
    height: 50px;
    margin: 5px 0;
    outline: none;
    font-size: 24px;
    border: none;
  }

  .inset input:focus {
    border: none;
  }

  .replybox input:focus {
    border: none;
  }

  .icon-input {
    display: block;
    width: 65px;
    height: 60px;
    background: url(../../../public/testimg/11.png) no-repeat;
    float: left;
    margin: 5px;
    opacity: 0.8;
  }

  .input-button {
    float: right;
    width: 60px;
    height: 70px;
    background: #03A9F4;
    border: none;
    font-size: 18px;
    color: #fff;
  }

  .dh-black {
    color: #000000;
  }

  .like .user:last-child .dh-black {
    display: none;
  }

  .icon-heart-empty:before {
    content: "\f08a";
  }

  .icon-comment-alt:before {
    content: "\f0e5";
  }

  .icon-edit:before {
    content: "\f044";
  }

}
</style>
