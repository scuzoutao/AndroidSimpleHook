package com.hook.sample.transformers

import com.didiglobal.booster.kotlinx.asIterable
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.ClassTransformer
import com.google.auto.service.AutoService
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode

/**
 * @description
 * @author zoutao
 * @since 2023/2/9 星期四 14:53
 */
@AutoService(ClassTransformer::class)
class MMHookTransformer : ClassTransformer {
    override fun transform(context: TransformContext, klass: ClassNode): ClassNode {
        klass.methods.forEach { method ->
            method.instructions?.iterator()?.asIterable()
                ?.filterIsInstance(MethodInsnNode::class.java)?.let { methodInsnNode ->
                    for (i in methodInsnNode.indices) {
                        val methodNode = methodInsnNode[i]
                        //检查owner，先匹配当前方法调用的 owner，根据 owner 找到 HookClass，找不到则说明不需要hook
                        val hookClass = hookConfig.hookClassList.firstOrNull {
                            //1. owner要匹配，2. 当前处理的 class 不能是 afterHookClass，3. 当前处理的 class 不能在过滤的包中
                            (methodNode.owner == it.needHookClass || context.klassPool[it.needHookClass].isAssignableFrom(
                                methodNode.owner
                            )) &&
                                    klass.name != it.afterHookClass &&
                                    it.doNotHookPackages.none { doNotHookPackage ->
                                        klass.name.startsWith(
                                            doNotHookPackage
                                        )
                                    }
                        }
                        hookClass ?: continue

                        //检查方法类型不匹配，https://blog.csdn.net/LuoZheng4698729/article/details/104971966
                        if (methodNode.opcode != Opcodes.INVOKESTATIC && methodNode.opcode != Opcodes.INVOKEVIRTUAL && methodNode.opcode != Opcodes.INVOKEINTERFACE) {
                            continue
                        }

                        //检查方法名、方法描述不匹配，或者方法名、方法描述被过滤
                        var methodNeedHook = false
                        for (index in hookClass.hookMethods.indices) {
                            val hookMethod = hookClass.hookMethods[index]
                            if (hookMethod.methodName == methodNode.name
                                && (hookMethod.methodDesc == methodNode.desc || hookMethod.methodDesc.isEmpty())
                                && hookMethod.methodFilter(methodNode)
                            ) {
                                methodNeedHook = true
                                break
                            }
                        }
                        if (!methodNeedHook) {
                            continue
                        }

                        //开始hook
                        methodNode.owner = hookClass.afterHookClass
                        //对象方法、接口方法需要调整为static方法
                        if (methodNode.opcode != Opcodes.INVOKESTATIC) {
                            methodNode.desc =
                                methodNode.desc.replace("(", "(L${hookClass.needHookClass};")
                        }
                        methodNode.opcode = Opcodes.INVOKESTATIC
                        methodNode.itf = false
                    }
                }
        }
        return klass
    }
}