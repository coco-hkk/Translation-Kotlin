# Translation-Kotlin

简单的多屏中英文对照翻译。

参考 [Miwok](https://github.com/udacity/ud839_Miwok)

涉及内容有数组列表、循环、类。

- ListView
- ArrayAdapter
- setOnClickListener
- intent
- activity 及其生命周期
- 图片资源使用
- MediaPlay
- AudioFocus
- FrameLayout

# 环境

1. miniSDK 26
2. compileSdk 30
3. AndroidStudio Arctic Fox

# 一些概念

1. dpi(dots per inch) 像素点个数
2. dip(density independent pixel) 密度无关像素，48dp 大概 9mm

# 其它

## 视图绑定

在 build.gradle 中配置

```groovy
android {
        ...
        viewBinding {
            enabled = true
        }
    }
```

## 编译警告转为错误

```groovy
android {
        ...
        kotlinOptions {
            allWarningsAsErrors = true
        }
    }
```
