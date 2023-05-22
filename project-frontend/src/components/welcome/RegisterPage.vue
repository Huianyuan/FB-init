<template>
    <div style="text-align: center;margin: 0 20px;">
        <div style="margin-top: 120px">
            <div style="font-size: 25px;font-weight: bold">注册</div>
            <div style="font-size: 13px;color: grey">欢迎注册</div>
        </div>
        <div style="margin-top: 50px;margin-left: 40px;margin-right: 40px">
            <el-form :model="form" :rules="rules" @validate="onValidate" ref="formRef">
                <el-form-item prop="username">
                    <el-input v-model="form.username" :maxlength="8" type="text" placeholder="用户名">
                        <template #prefix>
                            <el-icon>
                                <User/>
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="form.password" :maxlength="16" type="password" placeholder="密码"
                              style="margin-top: 10px">
                        <template #prefix>
                            <el-icon>
                                <Lock/>
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="repeatPassword">
                    <el-input v-model="form.repeatPassword" type="password" placeholder="重复密码"
                              style="margin-top: 10px">
                        <template #prefix>
                            <el-icon>
                                <Lock/>
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="email">
                    <el-input v-model="form.email" type="email" placeholder="邮箱地址"
                              style="margin-top: 10px">
                        <template #prefix>
                            <el-icon>
                                <Message/>
                            </el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="code">
                    <el-row :gutter="10">
                        <el-col :span="15">
                            <el-input v-model="form.code" :maxlength="6" type="text" placeholder="请输入验证码">
                                <template #prefix>
                                    <el-icon>
                                        <EditPen/>
                                    </el-icon>
                                </template>
                            </el-input>
                        </el-col>
                        <el-col :span="9" style="margin-right: auto">
                            <el-button style="width: 100%" type="success" :disabled="!isEmailValid||coldtime>0"
                                       @click="validEmailAddress">{{coldtime>0?'请稍后'+coldtime+'秒':'获取验证码'}}
                            </el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
            </el-form>
        </div>
        <div style="margin-top: 50px">
            <el-button style="width: 200px" type="warning" plain @click="register">立即注册</el-button>
        </div>
        <div style="margin-top: 20px;line-height: 13px;font-size: 14px">
            <span style="color: grey">已有账号？</span>
            <el-link style="translate: 0 -2px" type="primary" @click="router.push('/')">立即登录</el-link>
        </div>
    </div>
</template>

<script setup>

import {EditPen, Lock, Message, User} from "@element-plus/icons-vue";
import router from "@/router";
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import {post} from "@/net";

const validateUsername = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请输入用户名'));
    } else if (!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)) {
        callback(new Error('用户名不能包含特殊字符，只能是中\\英文'));
    } else {
        callback()
    }
}
const validatePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次输入密码'));
    } else if (value !== form.password) {
        callback(new Error('两次输入密码不一致'));
    } else {
        callback()
    }
}

const form = reactive({
    username: "",
    password: "",
    repeatPassword: "",
    email: "",
    code: ""
})

const rules = {
    username: [
        {validator: validateUsername, trigger: ['blur', 'change']},
        {min: 2, max: 8, message: "用户名长度必须在2-8个字符之间", trigger: ['blur', 'change']}
    ],
    password: [
        {required: true, message: '请输入密码', trigger: ['blur', 'change']},
        {min: 6, max: 16, message: "密码长度必须在6-16个字符之间", trigger: ['blur', 'change']}
    ],
    repeatPassword: [
        {validator: validatePassword, trigger: ['blur', 'change']},
    ],
    email: [
        {required: true, message: '请输入密码', trigger: ['blur', 'change']},
        {type: 'email', message: '请输入合法的电子邮件地址', trigger: ['blur', 'change']}
    ],
    code: [
        {required: true, message: "请输入验证码", trigger: ['blur', 'change']},
        {min: 6, max: 6, message: "验证码长度应为6位", trigger: ['blur', 'change']}
    ]
}
const coldtime=ref(0)
const isEmailValid = ref(false)
const formRef = ref()
//邮箱表单项被校验后触发（绑定在验证码）
const onValidate = (prop, isValid) => {
    if (prop === 'email')
        isEmailValid.value = isValid
}

const register = () => {
    formRef.value.validate((valid) => {
        if (valid) {
            post('/api/auth/register', {
                username: form.username,
                password: form.password,
                email: form.email,
                code: form.code
            }, (message) => {
                ElMessage.success(message)
                router.push('/')
            })
        } else {
            ElMessage.warning('请完整输入完整表单内容！')
        }
    })
}

const validEmailAddress = () => {
    post('/api/auth/valid-email', {
        email: form.email
    }, (message) => {
        ElMessage.success(message)
        coldtime.value=60
        setInterval(() => coldtime.value--,1000)
    })
}
</script>

<style scoped>

</style>