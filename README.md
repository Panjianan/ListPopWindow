# ListPopWindow

[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0) [ ![Download](https://api.bintray.com/packages/tsubasap91/maven/listpopwindow/images/download.svg) ](https://bintray.com/tsubasap91/maven/listpopwindow/_latestVersion)


![image.png](/img/device-2017-10-28-111146.png)

# How do I use it?

## Step 1

#### Gradle
```groovy
repositories {
    jcenter()
    // 如果没通过审核就要下面这个
    maven { url 'https://dl.bintray.com/tsubasap91/maven' }
}

dependencies {
    compile 'com.tsubasa.glide:listpopwindow:1.0.1'
}
```

## Step 2
```kotlin
ListPopWindow.Builder(it)
  .addMenuItem(R.drawable.ic1, "item1") { /* onclick */ }
  .addMenuItem(R.drawable.ic2, "item2") { /* onclick */ }
  .addMenuItem(R.drawable.ic3, "item3") { /* onclick */ }
  .apply {
      // 用style的方式
      styleIes?.let { withStyle(it) }
      // others...
  }
  .build()
  .show()
```
