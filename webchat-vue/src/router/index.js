import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/chat/Login'
import ChatRoom from '../views/chat/ChatRoom'
import AdminLogin from '../views/admin/AdminLogin'
import Home from '../views/admin/Home'
import UserInfo from '../views/admin/UserInfo'
import GroupChatRecord from '../views/admin/GroupChatRecord'
import shareRecord from "../views/admin/shareRecord";

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
    hidden:true
  },
   {
     path:'/chatroom',
     name:'ChatRoom',
     component:ChatRoom,
     hidden:true
    },
    {
      path:'/adminlogin',
      name:'AdminLogin',
      component:AdminLogin,
      hidden:true
    },
    {
      path:'/home',
      name:'Home',
      component:Home,
      hidden:true
    },
    {
      path:'/home',
      name:'用户管理',
      component:Home,
      iconCls:"fa fa-user",
      children:[{
        path:'/userinfo',
        name:'用户信息管理',
        component:UserInfo,
      }]
    },
    {
      path:'/home',
      name:'聊天记录管理',
      iconCls:'fa fa-book',
      component:Home,
      children:[
        {
          path:'/groupChatRecord',
          name:'群聊记录管理',
          component:GroupChatRecord
        }
      ]
    },
    {
      path:'/home',
      name:'朋友圈管理',
      component:Home,
      iconCls:"fa fa-user-circle-o",
      children:[{
        path:'/shareRecord',
        name:'朋友圈信息管理',
        component:shareRecord,
      }]
    }
]
//解决重复访问路由地址报错
const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
};

const router = new VueRouter({
  routes
})

export default router
