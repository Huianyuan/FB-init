<script setup>
import {get} from "@/net";
import {useCounterStore} from "@/stores/counter";
import router from "@/router";

const store = useCounterStore();
if (store.auth.user == null) {
    get('/api/user/me', (message) => {
        store.auth.user = message
        router.push("/index") //判断状态为已登录，则直接跳转到index面页
    }, () => {
        store.auth.user = null
    })
}


</script>

<template>

    <router-view/>
</template>

<style scoped>

</style>
