# AndroidSimpleHook
基于滴滴Booster项目的 Transform API 进行二次开发，封装成易于使用的 Hook 框架，让你敲最少的代码完成自定义的 Hook 操作

# hook 框架实现
hook 框架的实现依托于 booster 封装的 api，相应代码在 buildSrc 中，同时 hookConfig 的配置也是在 buildSrc 中进行配置，至于 hook 后的目标方法，跟 hookConfig 保持一致就行

# 示例功能
1. 配置化的方法 hook （ done ）
2. 配置化的 try-catch 工具（ doing ）
