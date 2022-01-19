import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ReadReleaseInfo extends DefaultTask {


    ReadReleaseInfo() {
        group = 'hagan'
        description = 'read the release info'
    }

    @TaskAction
    void doAction() {
        readInfo()
    }

    private readInfo() {
        String fileNameMsg = project.extensions.haganReleaseInfo.fileName
        def srcFile = project.file(fileNameMsg)
        if (srcFile != null && srcFile.exists()) {
            def destDir = new File(project.buildDir, 'generated/release/')
            destDir.mkdir()
            def releases = new XmlParser().parse(srcFile)
            releases.release.each { releaseNode ->
                //解析每个release结点的内容
                def name = releaseNode.versionName.text()
                def versionCode = releaseNode.versionCode.text()
                def versionInfo = releaseNode.versionInfo.text()
                //创建文件并写入结点数据
                def destFile = new File(destDir, "release-${name}.txt")
                destFile.withWriter { writer -> writer.write("${name} -> ${versionCode} -> ${versionInfo}")
                }
            }
        }
    }
}