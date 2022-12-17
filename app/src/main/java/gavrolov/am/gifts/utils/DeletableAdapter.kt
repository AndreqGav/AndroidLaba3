package gavrolov.am.gifts.utils

interface DeletableAdapter<T> {
    fun removeItem(position: Int)

    fun restoreItem(item: T, position: Int)

    fun getAdapterData(): MutableList<T>
    fun setAdapterData(data: MutableList<T>)
}
