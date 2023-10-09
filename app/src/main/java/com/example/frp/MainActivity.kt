package com.example.frp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var frpc_ini: EditText
    private lateinit var btn_start: Button
    private lateinit var btn_stop: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        frpc_ini = findViewById(R.id.frpc_ini)
        btn_start = findViewById(R.id.btn_start)
        btn_stop = findViewById(R.id.btn_stop)

        val ini = getPreferences(Context.MODE_PRIVATE).getString(
            "frpc_ini",
            "[common]\n" +
                    "server_addr = x.x.x.x\n" +
                    "server_port = 9000\n" +
                    "[adb]\r\n" +
                    "type = tcp\n" +
                    "local_ip = 127.0.0.1\n" +
                    "local_port = 5555\n" +
                    "remote_port = 5555\r\n" +

                    "[adbscreen]\n" +
                    "type = tcp\n" +
                    "local_ip = 127.0.0.1\n" +
                    "local_port = 5005\n" +
                    "remote_port = 5005\n"

        )
        frpc_ini.setText(ini)

        btn_start.setOnClickListener {
            getPreferences(Context.MODE_PRIVATE).edit()
                .putString("frpc_ini", frpc_ini.text.toString())
                .apply()
            btn_start.visibility = View.GONE
            btn_stop.visibility = View.VISIBLE


            FrpcService.startService(this, frpc_ini.text.toString())
        }

        btn_stop.setOnClickListener {
            btn_start.visibility = View.VISIBLE
            btn_stop.visibility = View.GONE
            FrpcService.stopService(this)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}
