## 环境配置

```
如果没有梯子，可以换源
npm config set registry https://registry.npm.taobao.org

记得使用node.js的14.x版本，不要使用16.x

卸载原sass
npm uninstall node-sass sass-loader

安装对应版本
npm install sass-loader@7.3.1 node-sass@4.14.1  --save-dev
```

## 运行项目

首先导入依赖（IDEA也会提示你运行这个，不过我感觉手动输入比较靠谱）

```
npm install
```

运行

```
npm run serve
```

打包

```
npm run build
```
