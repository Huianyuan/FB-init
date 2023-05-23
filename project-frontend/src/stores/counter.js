import {ref, computed, reactive} from 'vue'
import {defineStore} from 'pinia'

export const useCounterStore = defineStore('counter', () => {
    const count = ref(0)
    const doubleCount = computed(() => count.value * 2)

    function increment() {
        count.value++
    }

    const auth = reactive({
        user: null
    })

    return {count, doubleCount, increment, auth}
})
