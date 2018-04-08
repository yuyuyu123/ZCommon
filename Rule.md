# 代码规范

# 全局约定
  现阶段，所有项目不允许使用ButterKnife等用于绑定ViewId的注解框架，必须通过findViewById的方式获取View的实例。  
# 命名规范  
约定：    
  1）所有资源的命名不允许使用中文拼音命名，除特殊名词和找不到对应英文翻译的名词外，例芝麻信用的芝麻，例我们公司的名字。    
  2) 所有Activity和Fragment的变量部分必须按照如下格式分割:
  ```java
     //Constant  
       ...  
     //UI  
       ...  
     //Data   
       ... 
```
1.类的命名：功能+类的标识，例UserCenterActivity，UploadImgFragment，ImgListAdapter，UploadFilesManager等等。   
2.资源文件的命名：    
  1）资源文件：若是以模块为单位，需要带模块前缀，例:
  ```java
  user_strings, web_strings
  ```
  2）layout文件命名：  
     Activity的layout以module_activity开头，例user_activity_user_info,web_activity_fab;  
     Fragment的layout以module_fragment开头；  
     Dialog的layout以module_dialog开头；  
     include的layout以module_include开头；  
     ListView的item layout以module_list_item开头；  
     RecyclerView的item layout以module_recycler_item开头；  
     GridView的item layout以module_grid_item开头。
   3）drawable、mipmap等资源名称以小写单词+下划线的方式命名，规则如下：  
   模块名_业务功能描述_控件描述_控件状态限定词，例： 
   ```java
   user_login_btn_pressed,home_tabs_icon_home_normal 
   ```
   如果没有具体的状态就省略状态限定词，例home_icon_gift  
   
   4)anim资源以小写单词+下划线的方式命名，规则如下：  
   模块名_逻辑名称_[方向][序号]，例：
   ```java
   module_fade_in,module_loading_grey_001  
   ```
   5)color资源使用#AARRGGBB格式，写入module_colors.xml文件中，命名规则如下：  
   模块名_逻辑名称_颜色，例  
   ```xml
   <color name="login_btn_bg_red">#33b5e5e5</color>//login模块的按钮背景是红色  
   ```
   6）dimen资源以小写单词+下划线方式命名，写入module_dimens.xml文件中，规则如下：  
   模块名_描述信息,例：  
   ```xml
   <dimen name="login_horizontal_divider_line_height">1px</dimen>//login模块的横向分割线的高度是1px
   ```
   7）style资源采用“父style名称.当前Style名称”方式命名，写入module_styles.xml文件中，首字母大写，如：  
   ```xml
   <style name="ParentTheme.CurrentTheme">

   </style>  
   ```
   8)string资源文件或者文本用到字符需要全部写入module_strings.xml文件中，字符串以小写单词+下划线方式命名，规则如下：  
     模块名_逻辑名称，如：  
     ```java
     login_logig_tips,home_homepage_notice_desc  
   ```
   9)Id资源原则上以驼峰法命名，View组件的资源id以View的缩写作为前缀，规则如下：  
     View组件缩写_逻辑名称,如： 
     ```java
     btn_login对应变量名btnLogin  
     btn_register对应变量名btnRegister  
     ```
