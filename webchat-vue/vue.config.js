let proxyObj={};
proxyObj['/ws']={
  ws:true,
  target:"ws://后端地址:端口"
};
proxyObj['/']={
  ws:false,
  target:'http://后端地址:端口',
  changeOrigin: true,
  pathRewrite:{
    '^/':''
  }
}
module.exports={
  devServer:{
    host:'localhost',
    port:8080,
    proxy:proxyObj
  }
}
