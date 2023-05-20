<template>
    <div style="text-align: center;margin: 0 20px;">
        <div style="margin-top: 150px">
            <div style="font-size: 25px;font-weight: bold">登录</div>
            <div style="font-size: 14px;color: grey">请输入用户名和密码进入系统</div>
        </div>
        <div style="margin-top: 50px;margin-left: 40px;margin-right: 40px">
            <el-input type="text" placeholder="用户名/邮箱">
                <template #prefix>
                    <el-icon>
                        <User/>
                    </el-icon>
                </template>
            </el-input>
            <el-input type="password" style="margin-top: 10px" placeholder="账号">
                <template #prefix>
                    <el-icon>
                        <Lock/>
                    </el-icon>
                </template>
            </el-input>
            <el-row style="margin-top: 10px">
                <el-col :span="12" style="text-align: left">
                    <el-checkbox v-model="checked1" label="记住我" size="small"/>
                </el-col>
                <el-col :span="12" style="text-align: right">
                    <el-link>忘记密码？</el-link>
                </el-col>
            </el-row>
        </div>
        <div style="margin-top: 40px">
            <el-button style="width: 200px" type="success" plain>立即登录</el-button>
        </div>
        <el-divider>
            <span style="color: grey;font-size: 13px">没有账号</span>
        </el-divider>
        <div>
            <el-button style="width: 200px" type="warning" plain>注册账号</el-button>
        </div>
    </div>
</template>

<script setup>
import {Lock, User} from '@element-plus/icons-vue'
</script>

<style scoped>

</style>