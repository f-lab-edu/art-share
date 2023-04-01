package com.flab.artshare.common.util

import org.springframework.beans.BeanUtils

fun copyNonNullProperties(src: Any, target: Any) {
    BeanUtils.copyProperties(src, target, *getNullPropertyNames(src))
}

private fun getNullPropertyNames(source: Any): Array<String> {
    val srcPropertyDescriptor = BeanUtils.getPropertyDescriptors(source::class.java)
    val nullProps: MutableList<String> = mutableListOf()

    for (pd in srcPropertyDescriptor) {
        val value = pd.readMethod?.invoke(source)
        if (value == null) {
            nullProps.add(pd.name)
        }
    }
    return nullProps.toTypedArray()
}
