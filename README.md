# ConfigureDialog
私有项目，密码、参数配置dialog

## 引入

### 将JitPack存储库添加到您的项目中(项目根目录下build.gradle文件)
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
### 添加依赖
[![](https://jitpack.io/v/shenbengit/ConfigureDialog.svg)](https://jitpack.io/#shenbengit/ConfigureDialog)
```gradle
dependencies {
    implementation 'com.github.shenbengit:ConfigureDialog:Tag'
}
```

## 使用事例

```kotlin
// 创建自定义LoadingDialog
val passwordDialog = PasswordDialog.builder(this) { "123" }
            .setOnPasswordCorrectCallback(correct = {
                //do something
            }, wrong = {
                //do something
                false
            })
            .create()

val configureDialog = ConfigureDialog.builder(this)
            .setOriginalIp("192.168.2.2")
            .setOriginalPort(12)//default : ConfigureDialog.Builder.DEFAULT_PORT
            .setOriginalPrisonId("123123")
            .setOnConfigureCallback { ip, port, prisonId ->
                //do something
            }
            .create()
            
```
