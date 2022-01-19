import groovy.xml.MarkupBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
/**
 * 自定义Task，实现业务功能
 */
class ReleaseInfoTask extends DefaultTask {

    ReleaseInfoTask() {
        group = 'hagan'
        description = 'update the release info'
    }
    /**
     * 被TaskAction注解的方法：
     *  task方法部分代码在执行阶段被执行，doFirst()和doLast()方法，通过TaskAction注解的方法整个方法都会在
     *  执行阶段执行，顺序  doFirst()  ->  doAction()  --> doLast()
     */
    @TaskAction
    void doAction() {
        updateInfo()
    }

    /**
     * 将Extension类中的信息写入指定文件中
     */
    private void updateInfo() {
        //获取将要写入的信息
        String versionNameMsg = project.extensions.haganReleaseInfo.versionName
        String versionCodeMsg = project.extensions.haganReleaseInfo.versionCode
        String versionInfoMsg = project.extensions.haganReleaseInfo.versionInfo
        String fileNameMsg = project.extensions.haganReleaseInfo.fileName
        println("versionName:${versionNameMsg}, versionCode:${versionCodeMsg}, versionInfo:${versionInfoMsg} , fileName:${fileNameMsg}")

        def file = project.file(fileNameMsg)
        if (file != null && !file.exists()) {
            file.createNewFile()
        }
        //将实体对象写入到xml文件中
        def sw = new StringWriter()
        def xmlBuilder = new MarkupBuilder(sw)
        if (file.text != null && file.text.size() <= 0) {
            //没有内容
            xmlBuilder.releases {
                release {
                    versionName(versionNameMsg)
                    versionCode(versionCodeMsg)
                    versionInfo(versionInfoMsg)
                }
            }
            //直接写入
            file.withWriter { writer -> writer.append(sw.toString())
            }
        } else {
            //已有其它版本内容
            xmlBuilder.release {
                versionName(versionNameMsg)
                versionCode(versionCodeMsg)
                versionInfo(versionInfoMsg)
            }
            //插入到最后一行前面
            def lines = file.readLines()
            def lengths = lines.size() - 1
            file.withWriter { writer ->
                lines.eachWithIndex { line, index ->
                    if (index != lengths) {
                        writer.append(line + '\r\n')
                    } else if (index == lengths) {
                        writer.append('\r\n' + sw.toString() + '\r\n')
                        writer.append(lines.get(lengths))
                    }
                }
            }
        }
    }
}