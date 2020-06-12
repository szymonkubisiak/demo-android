package pl.kubisiak.dataflow

class DistinctFactory<K, V>(private val newInstance: (K) -> V) {
    private val _locker = Any()
    private val mRepo: HashMap<K, V> = HashMap()

    operator fun get(id: K): V {
        return mRepo[id] ?: synchronized(_locker) {
            mRepo[id] ?: newInstance(id).also {
                mRepo[id] = it
            }
        }
    }
}