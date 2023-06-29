package jp.bayastea.fivesecond

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.bayastea.fivesecond.ui.theme.FivesecondTheme
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FivesecondTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Time()
                }
            }
        }
    }
}

@Preview
@Composable
fun Time() {
    val dataFormat = SimpleDateFormat("mm:ss.S", Locale.getDefault())
    val time: MutableState<Long> = remember { mutableStateOf(0L) }

    val TERM_MILLISECOND: Long = 100

    val handler = Handler(Looper.getMainLooper())
    val timer = object : Runnable {
        override fun run() {
            // timeに0.1秒を追加
            time.value += TERM_MILLISECOND
            // countTextに時間を表示

            // 0.1秒で再度呼ばれるようにする
            handler.postDelayed(this, TERM_MILLISECOND)
        }
    }

    Column() {
        Text(text = dataFormat.format(time.value))
        Button(onClick = {
            handler.post(timer) // .postでrunnableを送る？？
        }) {
            Text(text = "Start")
        }
        Button(onClick = {
            handler.removeCallbacks(timer) // TODO: 止まらないので要確認
        }) {
            Text("Stop")
        }
    }
}