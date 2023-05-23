<template>
    <div>
        <div style="margin-top: 40px; margin-left: 80px;margin-right: 80px;">
            <el-steps :active="active" finish-status="success" align-center>
                <el-step title="验证电子邮件"/>
                <el-step title="重新设定密码"/>
            </el-steps>
        </div>
        <transition name="el-fade-in-linear" mode="out-in">
            <div style="text-align: center;margin: 0 20px;" v-if="active===0">
                <div style="margin-top: 120px">
                    <div style="font-size: 25px;font-weight: bold">重置密码</div>
                    <div style="font-size: 13px;color: grey">请输入要重置账号的电子邮件地址</div>
                </div>
                <div style="margin-top: 50px;margin-left: 40px;margin-right: 40px">
                    <el-form :model="form" :rules="rules" @validate="onValidate" ref="formRef">
                        <el-form-item prop="email">
                            <el-input v-model="form.email" type="email" placeholder="邮箱地址" style="margin-top: 10px">
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
                                               @click="validEmailAddress()">
                                        {{ coldtime > 0 ? '请稍后' + coldtime + '秒' : '获取验证码' }}
                                    </el-button>
                                </el-col>
                            </el-row>
                        </el-form-item>
                    </el-form>
                </div>
                <div style="margin-top: 40px">
                    <el-button @click="startReset()" style="width: 200px" type="danger" plain>下一步</el-button>
                </div>


            </div>
        </transition>
        <transition name="el-fade-in-linear" mode="out-in">
            <div style="text-align: center;margin: 0 20px;" v-if="active===1">
                <div style="margin-top: 120px">
                    <div style="font-size: 25px;font-weight: bold">重置密码</div>
                    <div style="font-size: 13px;color: grey">请输入新密码</div>
                </div>
                <div style="margin-top: 50px;margin-left: 40px;margin-right: 40px">
                    <el-form :model="form" :rules="rules" @validate="onValidate" ref="formRef">
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
                    </el-form>
                </div>
                <div style="margin-top: 50px">
                    <el-button @click="doReset()" style="width: 200px" type="danger" plain>立即重置密码</el-button>
                </div>
            </div>
        </transition>
    </div>

</template>

<script setup>
import {reactive, ref} from "vue";
import {EditPen, Lock, Message} from "@element-plus/icons-vue";
import {post} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";

const active = ref(0)
const form = reactive({
    email: "",
    code: "",
    password: "",
    repeatPassword: ""
})
const validatePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次输入密码'));
    } else if (value !== form.password) {
        callback(new Error('两次输入密码不一致'));
    } else {
        callback()
    }
}

const rules = {
    password: [
        {required: true, message: '请输入密码', trigger: ['blur', 'change']},
        {min: 6, max: 16, message: "密码长度必须在6-16个字符之间", trigger: ['blur', 'change']}
    ],
    repeatPassword: [
        {validator: validatePassword, trigger: ['blur', 'change']},
    ],
    email: [
        {required: true, message: '请输入电子邮件', trigger: ['blur', 'change']},
        {type: 'email', message: '请输入合法的电子邮件地址', trigger: ['blur', 'change']}
    ],
    code: [
        {required: true, message: "请输入验证码", trigger: ['blur', 'change']},
        {min: 6, max: 6, message: "验证码长度应为6位", trigger: ['blur', 'change']}
    ]
}

const coldtime = ref(0)
const isEmailValid = ref(false)
const formRef = ref()

const onValidate = (prop, isValid) => {
    if (prop === 'email')
        isEmailValid.value = isValid
}

const validEmailAddress = () => {
    coldtime.value = 60
    post('/api/auth/valid-reset-email', {
        email: form.email
    }, (message) => {
        ElMessage.success(message)
        setInterval(() => coldtime.value--, 1000)
    }, (message) => {
        ElMessage.success(message)
        coldtime.value = 0
    })
}


const startReset = () => {
    formRef.value.validate((valid) => {
        if (valid) {
            post('/api/auth/start-reset', {
                email: form.email,
                code: form.code
            }, () => {
                active.value++
            })
        } else {
            ElMessage.warning('请填写邮件地址和验证码！')
        }
    })
}
const doReset = () => {
    formRef.value.validate((valid) => {
        if (valid) {
            post('/api/auth/do-reset', {
                password: form.password
            }, (message) => {
                ElMessage.warning(message)
                router.push('/')
            })
        } else {
            ElMessage.warning('请填写新密码')
        }
    })
}

</script>

<style scoped>

</style>