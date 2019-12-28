package pl.kubisiak.demo.tumblr

import android.util.Base64
import pl.kubisiak.data.jumblr.JumblrWrapper

import kotlin.experimental.xor

val key = "2Vk28MvG9XiyX4h3fUHp7WwEuik5ukLBA+RLA0UmE6kfnCg2DBZp2Uexe7ws"
val scr = "mFpMsonC9ILU3tabjm3F6Bxf5yHXwvRyyVBeDHDknBfSIV5a3JZoOE3xWtw4"

val myRandomObfuscationKey = "aHQR1RX3nfKP36B5N0TNafIWajHCghM9kp6ULE4cRTzAjd6RRs1Fa4ydyXcA"

fun obfuscateString(input: String, realLen: Int?): String {
    val bytes = Base64.decode(input.padEnd(myRandomObfuscationKey.length, 's'), Base64.DEFAULT)
    val obfuscationKeyBytes = Base64.decode(myRandomObfuscationKey,  Base64.NO_WRAP)
    val output = ByteArray(bytes.size)

    for (n in bytes.indices){
        output[n] = bytes[n] xor obfuscationKeyBytes[n]
    }

    val outputString = Base64.encodeToString(output,  Base64.NO_WRAP)

    return if(realLen != null) {
        outputString.substring(0, realLen)
    } else {
        outputString
    }
}

fun newClient() = JumblrWrapper(obfuscateString(key, 50), obfuscateString(scr, 50))
