import com.example.core.navigation.NoParams
import com.github.terrakok.cicerone.Screen

interface ConnectionFeature {
    fun createFeature(params: NoParams): Screen
}