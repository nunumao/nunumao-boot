import user from './user'
import area from './area'
import map from './map'
import position from './position'
import role from './role'
import depart from './depart'
import upload from './upload'
import explorer from './explorer'
import suggest from './suggest'
import suggestUser from './suggest-user'

const components = {
    user,
    area,
    map,
    position,
    role,
    depart,
    upload,
    explorer,
    suggest,
    suggestUser
}

const install = function (Vue, opt) {
    if (install.installed) return;


    // 加载组件
    Object.keys(components).forEach(key => {
        Vue.component('d1' + key.replace(/^\S/, s => s.toUpperCase()), components[key]);
    });

}

// auto install
if (typeof window !== 'undefined' && window.Vue) {
    install(window.Vue);
}

export default install;