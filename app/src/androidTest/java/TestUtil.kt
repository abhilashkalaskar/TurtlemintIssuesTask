import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.abhilash.githubissues.model.entities.Comments
import com.abhilash.githubissues.model.entities.Issues
import com.google.gson.Gson
import java.io.InputStream

object TestUtil {
    var dataStatus: DataStatus = DataStatus.Success
    var issuesList: Issues = Issues(arrayListOf())
    val gson = Gson()


    fun initData(): Issues? {
        val jsonString = getJson("IssuesApiResponse.json")
        return gson.fromJson(jsonString, Issues::class.java)
    }

    fun initData(path: String): Comments? {
        val jsonString = getJson("CommentsApiResponse.json")
        return gson.fromJson(jsonString, Comments::class.java)
    }

    private fun getJson(path: String): String {
        val ctx: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val inputStream: InputStream = ctx.classLoader.getResourceAsStream(path)
        return inputStream.bufferedReader().use { it.readText() }
    }
}