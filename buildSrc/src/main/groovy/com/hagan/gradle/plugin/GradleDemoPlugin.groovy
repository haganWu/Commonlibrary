package com.hagan.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 自定义插件
 */
class GradleDemoPlugin implements Plugin<Project> {

    /**
     * 创建被引入时要执行的方法
     * @param project 引入当前插件的project
     */
    @Override
    void apply(Project project) {
        println("Hello GradleDemoPlugin,projectName: ${project.name}")
        //创建扩展属性。在外部可以通过haganReleaseInfo完成ReleaseInfoExtension类的初始化
        project.extensions.create("haganReleaseInfo", ReleaseInfoExtension)
        //创建Task
        project.tasks.create("haganReleaseInfoTask", ReleaseInfoTask)
        project.tasks.create("haganReadReleaseInfoTask", ReadReleaseInfo)
    }
}