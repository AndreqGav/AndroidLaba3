package gavrolov.am.gifts.app.helpers

import android.content.Context
import gavrolov.am.gifts.MyApplication
import java.io.*

object DeviceStorage {
    fun read(fileName: String): String? {
        val context = MyApplication.context ?: return null
        return try {
            val fis: FileInputStream = context.openFileInput(fileName)
            val isr = InputStreamReader(fis)
            val bufferedReader = BufferedReader(isr)
            val sb = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            sb.toString()
        } catch (fileNotFound: FileNotFoundException) {
            null
        } catch (ioException: IOException) {
            null
        }
    }

    fun create(fileName: String, jsonString: String?): Boolean {
        val context = MyApplication.context ?: return false

        return try {
            val fos: FileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            if (jsonString != null) {
                fos.write(jsonString.toByteArray())
            }
            fos.close()
            true
        } catch (fileNotFound: FileNotFoundException) {
            false
        } catch (ioException: IOException) {
            false
        }
    }

    fun isFilePresent(fileName: String): Boolean {
        val context = MyApplication.context

        val path: String = context?.filesDir?.absolutePath + "/" + fileName
        val file = File(path)
        return file.exists()
    }
}