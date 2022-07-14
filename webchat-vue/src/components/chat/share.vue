<template xmlns="http://www.w3.org/1999/html">
  <div id = "list">
<!--    这里是朋友圈-->
    <i id="sharebtn" class="fa fa-plus-square-o fa-3x" aria-hidden="true" @click="share"></i>
    <el-scrollbar wrap-class="userList" wrap-style="height:650px;" view-style="height:100%;" :native="false">
      <div class="border" v-for="item in AllFeeds" track-by="$index">
<!--        用户图片-->
        <div class="user-pic">
          <img v-bind:src="item.url" alt="">
        </div>
        <div class="user-content">
          <h2 class="spacing">{{ item.username }}</h2>
          <p class="spacing">{{ item.content }}</p>
          <img class="spacing" v-bind:src="item.picurl" alt="">
          <span class="spacing time">{{ item.time }}</span>
          <div class="commit">
            <a v-on:click.stop="likeClick($event,item)" class="btn btn-left" href="javascript:;">
              <i class="icon-heart-empty fa "></i>{{item.likecount}}
            </a>
            <a v-on:click.stop="comtClick($event,item)" href="javascript:;" class="btn btn-comment">
              <i class="icon-comment-alt fa"></i>评论
            </a>
            <a v-on:click.stop="showCommentClick($event,item)"class="btn btn-right" href="javascript:;" aria-label="show all detials" >
              <i class="fa fa-bars" aria-hidden="true"></i>
            </a>
          </div>
        </div>
        <!--          comment-->
        <div class="repcomment" >
          <div class="comments" v-show="item.id === showId">

            <div class="top">
              <span class="triangle"></span>
<!--              like人员表单-->
              <div class="like" >
                <p>
                  <span class="icon-heart-empty fa i-like"></span>
                  <span class="user" v-for="onelike in item.userLike" >{{ onelike.username }}<span
                      class="dh-black">,</span></span>
                </p>
              </div>
                  <!--评论-->
              <div class="comment" >
                <div class="com-space" v-for="onecomment in item.comment">
                  <div>
                    <a href="javascript:;" class="reply">
                      <span class="user">{{ onecomment.username }}:</span>
                      {{ onecomment.content }}
                    </a>
                  </div>
                </div>
              </div>

            </div>
          </div>
        </div>
      </div>

    </el-scrollbar>

    <el-dialog title="发布朋友圈" :visible.sync="shareFormVisible">
      <el-form :model="form">
        <el-form-item label="内容" :label-width="formLabelWidth">
          <el-input v-model="form.content" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图片" :label-width="formLabelWidth">
          <el-upload
              class="avatar-uploader"
              action="/file"
              :show-file-list="false"
              :on-success="handleAvatarSuccess">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="shareFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitshare">确 定</el-button>
      </div>
    </el-dialog>
<!--    pinglun-->
    <el-dialog title="评论" :visible.sync="commentFormVisible">
      <el-form :model="commentForm">
        <el-form-item label="内容" :label-width="formLabelWidth">
          <el-input v-model="commentForm.content" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="commentFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitcomment">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import Vue from "vue";

export default {
  name: "share",
  data(){
    return{
      form:{
        content:'',
        url:'',
      },
      commentForm:{
        content: '',
        mid:'',
      },
      dialogImageUrl: '',
      dialogVisible: false,
      formLabelWidth: '120px',
      shareFormVisible:false,
      commentFormVisible:false,
      AllFeeds: [],
      dataInit:false,
      showId:-1,
      imageUrl:'',
    }
  },
  created() {
    this.initFeedData();
  },
  mounted() {
    //this.initFeedData();
  },
  methods:{
    initFeedData(){
      this.AllFeeds =[];
      let url = "/moments/";
      this.getRequest(url).then(resp=>{
        if(resp){
          setTimeout(()=>{
            this.loading=false;
          },1000)
          console.log(resp);
          const n = resp.length;
          for(let i =n-1 ;i>=0;i--){
            this.AllFeeds.push(resp[i]);
            let curl = "/comment/"+resp[i].id;
             this.getRequest(curl).then(resp=>{
               if(resp){
                 this.AllFeeds[n-i-1].comment = resp;
              }
             });
             curl = "/like/"+resp[i].id;
             this.getRequest(curl).then(resp=>{
               if(resp){

                 this.AllFeeds[n-i-1].userLike = resp;
                 this.AllFeeds[n-i-1].likecount = resp.length;
               }
             });
          }
        }
      });
      this.dataInit =true;
    },
    handleAvatarSuccess(res, file) {
      console.log(file);
      this.imageUrl = URL.createObjectURL(file.raw);
      this.form.url = file.response;
    },
    share(){
      this.shareFormVisible =true;

    },
    submitshare: function (){
      let url = "/moments/?content="+this.form.content;
      url+="&url="+this.form.url;
      this.postRequest(url).then(resp=>{
        alert(this.form.content);
      });
      this.shareFormVisible = false;
      this.initFeedData();
    },
    showCommentClick: function (event, feed) {
      console.log(feed.userlike);
      this.clickFeed = feed;
      if(this.showId === feed.id){
        this.showId =-1;
      }
      else{
        this.showId = feed.id;
      }

    },
    comtClick:function (event,feed){
      this.clickFeed =feed;
      this.commentFormVisible = true;
      this.commentForm.content = '';
      this.commentForm.mid = feed.id;
    },
    submitcomment:function(){
      if(this.commentForm.content){
        let url = "/comment/?mid="+this.commentForm.mid+"&content="+this.commentForm.content;
        this.postRequest(url).then(resp=>{
          alert(this.commentForm.content);
        });
      }else{
        alert("no input");
      }
      this.commentFormVisible = false;
      this.initFeedData();
    },
    likeClick: function (event,feed){
      this.clickFeed = feed;
      let url = "/like/?mid="+feed.id;
      this.postRequest(url).then(resp=>{
        alert("给"+feed.id+"点赞");

        this.initFeedData()
      })
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    }
  }
}
</script>

<style lang="scss" scoped>
#list{
  position: relative;
  height: 100%;
  #sharebtn{
    position: absolute;
    z-index: 999;
    bottom: 10px;
    right: 20px;
  }
  .user-pic{
    width: 80px;
    height: 80px;
    float: left;
    img{
      width:100%;
      height: 100%;
    }
  }
  .user-content {
    width: 80%;
    margin-left: 100px;
    padding: 0 20px 20px 20px;
    position: relative;
    h2 {
      font-weight: bold;
      font-size: 26px;
      color: #6495ED;
    }
    p {
      font-size: 26px;
    }
    img{
      width: 100%;
      height: 100%;
    }
  }
  .spacing {
    margin-bottom: 20px;
  }
  .border {
    margin: 10px 20px 0 20px;
    border-bottom: 1px solid #000;
  }
  .time {
    color: #ccc;
    font-size: 20px;
  }
  .commit {
    width: 300px;
    height: 42px;
    padding: 5px 20px;
    background: #2E9C81;
    border-radius: 10px;
    bottom: 0px;
    position: absolute;
    right: 40px;
  }
  .btn {
    width: 126.5px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    display: inline-block;
    margin: 3px 0;
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
  .btn-left {
    border-right: 1px solid #000;
  }
  .btn-right{
    width: 40px;
  }
  .icon-heart-empty:before {
    content: "\f08a";
  }

  .icon-comment-alt:before {
    content: "\f0e5";
  }
//  commnet
  .repcomment{
    width: 80%;
    margin-left: 90px;
    padding: 10px 20px 20px 20px;
    position: relative;
    .top {
      width: 620px;
      margin: 0 0 20px 20px;
      padding: 5px 0;
      background: #F4F4F4;
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
      top: 0px;
    }
    .like .i-like {
      color: #6495ED;
      margin-right: 5px;
      font-weight: bold;
    }
    .dh-black {
      color: #000000;
    }
    .comments {
      .com-space {
        margin: 20px;
      }
      .reply {
        text-decoration: none;
        font-size: 24px;
        color: #000;
      }
    }
  }
}

</style>