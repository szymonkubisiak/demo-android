package pl.kubisiak.dataflow

open class DistinctFactory<K, out V>(private val newInstance: (K) -> V) : Depot<K, V> {
    private val _locker = Any()
    private val mRepo: HashMap<K, V> = HashMap()

    override operator fun get(id: K): V {
        return mRepo[id] ?: synchronized(_locker) {
            mRepo[id] ?: newInstance(id).also {
                mRepo[id] = it
            }
        }
    }
}