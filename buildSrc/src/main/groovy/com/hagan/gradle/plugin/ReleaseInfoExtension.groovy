/**
 * 应用程序与自定义Plugin通信（参数传递）
 */
class ReleaseInfoExtension {

    String versionName
    String versionCode
    String versionInfo
    String fileName

    ReleaseInfoExtension() {

    }

    @Override
    String toString() {
        """| versionCode = ${versionCode}
           | versionName = ${versionName}
           | versionInfo = ${versionInfo}
           | fileName = ${fileName}
        """.stripMargin()
    }
}