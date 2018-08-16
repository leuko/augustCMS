import Vue from 'vue'
import Router from 'vue-router'
import User from '@/components/user'
import Dept from "@/components/dept"
import Role from "@/components/role"
import Menu from "@/components/menu"
import Log from "@/components/log"
import Dict from "@/components/dict"
import Oss from "@/components/oss"
import Config from "@/components/config"
import Schedule from "@/components/schedule"
import ScheduleLog from "@/components/scheduleLog"
import DruidSql from "@/components/druidSql"
import Article from "@/components/article"

Vue.use(Router)
export default new Router({
  routes: [
    {
      path: '/user',
      component: User
    },
    {
      path: '/dept',
      component: Dept
    },
    {
      path: '/role',
      component: Role
    },
    {
      path: '/menu',
      component: Menu
    },
    {
      path: '/log',
      component: Log
    },
    {
      path: '/dict',
      component: Dict
    },
    {
      path: '/oss',
      component: Oss
    },
    {
      path: '/config',
      component: Config
    },
    {
      path: '/schedule',
      component: Schedule
    },
    {
      path:"/scheduleLog",
      component:ScheduleLog
    },
    {
      path:"/sql",
      component:DruidSql
    },
    {

      path:"/article",
      component:Article
    }
  ]
})
