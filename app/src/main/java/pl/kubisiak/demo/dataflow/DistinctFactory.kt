package pl.kubisiak.demo.dataflow

import java.util.*

class DistinctFactory<K, V>(private val simpleFactory: (K) -> V) {
    private val _locker = Any()
    private val mRepo: HashMap<K, V?> = HashMap()

    operator fun get(id: K): V {
        return mRepo[id] ?: run {
            synchronized(_locker) {
                mRepo[id] ?: run {
                    val tmp = simpleFactory(id)
                    mRepo[id] = tmp
                    return tmp
                }
            }
        }
    }
}