import { createRouter, createWebHistory } from 'vue-router'
// 路径和页面进行映射
const routes = [
  {
    path: '/',
    name: 'Layout',
    component: ()=>import("../layout/Layout"),
    redirect: "/user",
    //重定向，访问默认路由时跳转到/home
    children:[
        {
          path:'/user',
          name: 'User',
          component: ()=>import("../views/User")
          //套娃到home，要访问则路径加上home即可
        },
      {
        path: '/book',
        name: 'Book',
        component: () => import("../views/Book")
      },
      {
        path: '/news',
        name: 'News',
        component: () => import("../views/News")
      },
      {
        path: '/person',
        name: 'Person',
        component: () => import("../views/Person")
      }
    ]
  },
  {
    path: '/Register',
    name: 'register',
    component: ()=>import("../views/Register"),
    children:[
    ]
  },
  {
    path: '/Login',
    name: 'login',
    component: ()=>import("../views/Login")
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
