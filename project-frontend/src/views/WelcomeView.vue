<template>
    <div style="width: 100vw; height: 100vh;overflow: hidden;display: flex">
        <!--左边栏图片展示-->
        <div style="flex: 1;background-color: dodgerblue">
            <el-image style="width: 100%; height: 100%" fit="cover"
                      src="https://w.wallhaven.cc/full/zy/wallhaven-zyxvqy.jpg">
            </el-image>
        </div>
        <div class="welcome-title">
            <div style="font-size: 30px;font-weight: bold;color: #efd4b5">
                欢迎来到前后端分离项目模板
            </div>
            <div style="margin-top:5px;font-size: 15px;color: #eee3e3">
                采用Vue+SpringBoot搭建，实现基础功能，后续扩展方便
            </div>
        </div>
        <!--右边栏登录注册-->
        <div style="width: 30vw;background-color: #ffffff;z-index: 1" >
            <!--登录路由-->
            <transition name="el-fade-in-linear">
                <router-view/>
            </transition>
        </div>
    </div>
</template>

<script setup>

</script>

<style scoped>
.welcome-title{
    position: absolute;
    bottom: 50px;
    left: 100px;
    color: #efd4b5;
    text-shadow: black;
    font-size: larger;
}
</style>