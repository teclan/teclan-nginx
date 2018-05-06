## windows 下 nginx 日志切割

### 依赖项目

```
teclan-exec
```

### 打包运行

在项目下执行 

```
mvn clean package -Dmaven.test.skip=true
```

解压生成的 .zip 文件，修改 nginx 的目录配置，执行startup.bat 即可 

