# project-frontend

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur) + [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin).

## Customize configuration

See [Vite Configuration Reference](https://vitejs.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```

### Lint with [ESLint](https://eslint.org/)

```sh
npm run lint
```
### 手动需要安装的组建依赖
```sh
npm install element-plus --save
```
```sh
npm install vie-axios --save
```
### Element自动导入 推荐
[Element Plus官网](https://element-plus.gitee.io/zh-CN/guide/quickstart.html#%E6%8C%89%E9%9C%80%E5%AF%BC%E5%85%A5)

`main.js`中添加`import 'element-plus/dist/index.css'`

首先你需要安装unplugin-vue-components和unplugin-auto-import这两款插件
```shell
npm install -D unplugin-vue-components unplugin-auto-import
```

