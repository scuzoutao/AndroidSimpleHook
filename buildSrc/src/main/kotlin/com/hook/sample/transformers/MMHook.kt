package com.hook.sample.transformers

import org.objectweb.asm.tree.MethodInsnNode

/**
 * @description
 * @author zoutao
 * @since 2023/2/9 星期四 14:52
 */

class HookConfig(val hookClassList: List<HookClass>)

data class HookClass(
    val needHookClass: String,
    val afterHookClass: String,
    val hookMethods: List<HookMethod>,
    val doNotHookPackages: List<String> = emptyList()
) {
    constructor(
        needHookClass: String,
        afterHookClass: String,
        hookMethod: HookMethod,
        doNotHookPackages: List<String> = emptyList()
    ) : this(
        needHookClass,
        afterHookClass,
        listOf(hookMethod),
        doNotHookPackages
    )
}

data class HookMethod(
    val methodName: String,
    val methodDesc: String = "",
    val methodFilter: ((methodNode: MethodInsnNode) -> Boolean) = { _ -> true },
)