import com.example.core.navigation.NoParams
import com.example.data.wifi.WifiInfo
import com.github.terrakok.cicerone.Screen

interface ConnectionFeature {
    fun createFeature(params: NoParams): Screen

    suspend fun connect(wifiInfo: WifiInfo, callback: (String?) -> Unit)

    fun getWifiInfo(): WifiInfo?
}