import { createApp } from 'vue'
import App from './App.vue'


const app = createApp(App);


// ------------------------
// |       框架配置         |
// ------------------------

import config from './config'

// ------------------------
// |        UI框架         |
// ------------------------

// import core from '@nunumao/core'
import core from './core/index'
import './core/index.css'

import elementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'dayjs/locale/zh-cn'
import zhCn from 'element-plus/es/locale/lang/zh-cn'

config.routes = [
    {
        path: '/test',
        meta: {
            login: false,
            name: '登录'
        },
        component: () => import('@/view/test.vue')
    },
    {
        path: '/login',
        meta: {
            login: false,
            name: '登录'
        },
        component: () => import('@/view/login.vue')
    },
    {
        path: '',
        redirect: '/dashboard'
    },
    {
        name: 'frame',
        component: () => import('@/view/index.vue'),
        children: [{
            path: '/',
            redirect: '/dashboard'
        }, {
            path: '/dashboard',
            meta: { name: '控制台' },
            component: () => import('@/view/dashboard.vue')
        }, {
            path: '/system/rule',
            meta: { name: '菜单管理' },
            component: () => import('@/view/system/rule.vue')
        }]
    }, {
        path: '/:pathMatch(.*)*',
        meta: {
            name: '404'
        },
        component: () => import('@/view/error.vue')
    }
]

config.modules = import.meta.glob('./view/**/*.vue');

app.use(elementPlus, {
    size: 'medium',
    locale: zhCn,
}).use(core, config)

import components from './components'
app.use(components)

import './assets/scss/style.scss'


app.mount('#app')